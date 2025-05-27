package jp.keisekisya.webapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistMstCustomerRequest implements RequestDto {
	@NotNull
	@Size(min = 1)
	private String companyId;

	@NotNull
	@Size(min = 1)
	private String customerName;

	private String customerAddr;
}
