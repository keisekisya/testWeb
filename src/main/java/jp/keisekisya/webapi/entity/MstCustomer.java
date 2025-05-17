package jp.keisekisya.webapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jp.keisekisya.webapi.entity.pk.MstCustomerPk;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import org.hibernate.annotations.*;

@Entity
@Table(name = "mst_customer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MstCustomer {

    @EmbeddedId
    private MstCustomerPk pk;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Column(name = "customer_addr", length = 100)
    private String customerAddr;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
