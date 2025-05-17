package jp.keisekisya.webapi.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DeleteMstCustomerRequest {
	@NotNull
	@Size(min = 1)
	private String companyId;

	@NotNull
	private UUID customerId;
}
