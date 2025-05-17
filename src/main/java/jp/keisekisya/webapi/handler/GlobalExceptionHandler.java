package jp.keisekisya.webapi.handler;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jp.keisekisya.webapi.dto.ErrorResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

	private final MessageSource messageSource;

	// バリデーションエラーをキャッチしてレスポンスを返す
	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorResponseDto> handleValidationException(BindException ex) {
		StringBuilder errorMessage = new StringBuilder();
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errorMessage.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("; ");
		}
		log.warn(errorMessage.toString());
		ErrorResponseDto errorResponse = new ErrorResponseDto("B00002", getMessage("B00002"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(InternalAuthenticationServiceException.class)
	public ResponseEntity<ErrorResponseDto> handleException(InternalAuthenticationServiceException ex) {
		log.error(ex.getMessage(), ex);
		ErrorResponseDto errorResponse = new ErrorResponseDto("B00004", getMessage("B00004"));
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
		log.error(ex.getMessage(), ex);
		ErrorResponseDto errorResponse = new ErrorResponseDto("E00001", getMessage("E00001"));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponseDto> handleException(BusinessException ex) {
		log.error(ex.getMessage());
		ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getCode(), getMessage(ex.getCode()));
		return ResponseEntity.status(ex.getStatus()).body(errorResponse);
	}

	/**
	 * エラーメッセージ取得
	 * 
	 * @param code コード
	 * @return メッセージ
	 */
	private String getMessage(final String code) {
		return messageSource.getMessage(code, null, Locale.getDefault());
	}
}
