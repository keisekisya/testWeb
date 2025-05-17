package jp.keisekisya.webapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMstCustomerRequest implements RequestDto {
	@NotNull
	@Size(min = 1)
	private String companyId;
}
