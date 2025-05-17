package jp.keisekisya.webapi.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jp.keisekisya.webapi.entity.pk.MstLoginPk;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mst_login")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MstLogin {

	@EmbeddedId
	private MstLoginPk pk;

	/** key */
	private String key;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp(source = SourceType.DB)
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
}
