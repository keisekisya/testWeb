package jp.keisekisya.webapi.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jp.keisekisya.webapi.dto.ErrorResponseDto;
import jp.keisekisya.webapi.util.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.val;

/**
 * 認証有効期限切れ
 */
@Component
@AllArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final MessageUtils messageUtils;
	private final ObjectMapper jsonMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json; charset=UTF-8");
		val errorResponse = new ErrorResponseDto("B00004", messageUtils.getMessage("B00004"));
		response.getWriter().write(jsonMapper.writeValueAsString(errorResponse));
	}
}
