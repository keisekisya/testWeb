package jp.keisekisya.webapi.bean;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jp.keisekisya.webapi.util.Const;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetMstCustomerBean {
	private String companyId;
	private UUID customerId;
	private String customerName;
	private String customerAddr;

	@JsonFormat(pattern = Const.DATE_FORMAT)
	private LocalDateTime createdAt;

	@JsonFormat(pattern = Const.DATE_FORMAT)
	private LocalDateTime updatedAt;
}
