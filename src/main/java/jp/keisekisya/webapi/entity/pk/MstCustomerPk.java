package jp.keisekisya.webapi.entity.pk;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MstCustomerPk implements Serializable {
	/** 会社ID */
    private String companyId;
    
    /** 顧客ID */
    private UUID customerId;
}
