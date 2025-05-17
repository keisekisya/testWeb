package jp.keisekisya.webapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDto implements ResponseDto {
	private String errorId;
	private String errorMessage;
}
