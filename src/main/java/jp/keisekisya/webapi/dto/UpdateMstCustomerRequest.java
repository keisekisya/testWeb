package jp.keisekisya.webapi.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateMstCustomerRequest {
	@NotNull
	@Size(min = 1)
	private String companyId;

	@NotNull
	private UUID customerId;

	@NotNull
	@Size(min = 1)
	private String customerName;

	private String customerAddr;
}
