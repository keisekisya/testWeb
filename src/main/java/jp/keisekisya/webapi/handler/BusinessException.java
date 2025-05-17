package jp.keisekisya.webapi.handler;

import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException {
	private String code;
	private HttpStatusCode status;
}
