package jp.keisekisya.webapi.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jp.keisekisya.webapi.dto.ErrorResponseDto;
import jp.keisekisya.webapi.util.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {
	private final MessageUtils messageUtils;

	// バリデーションエラーをキャッチしてレスポンスを返す
	@ExceptionHandler(BindException.class)
	public ResponseEntity<ErrorResponseDto> handleValidationException(BindException ex) {
		StringBuilder errorMessage = new StringBuilder();
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errorMessage.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append("; ");
		}
		log.warn(errorMessage.toString());
		val errorResponse = new ErrorResponseDto("B00002", messageUtils.getMessage("B00002"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
	}

	@ExceptionHandler(InternalAuthenticationServiceException.class)
	public ResponseEntity<ErrorResponseDto> handleException(InternalAuthenticationServiceException ex) {
		log.error(ex.getMessage(), ex);
		val errorResponse = new ErrorResponseDto("B00004", messageUtils.getMessage("B00004"));
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
		log.error(ex.getMessage(), ex);
		val errorResponse = new ErrorResponseDto("E00001", messageUtils.getMessage("E00001"));
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponseDto> handleException(BusinessException ex) {
		log.error(ex.getMessage());
		val errorResponse = new ErrorResponseDto(ex.getCode(), messageUtils.getMessage(ex.getCode()));
		return ResponseEntity.status(ex.getStatus()).body(errorResponse);
	}
}
