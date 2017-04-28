package defunct.store.core.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@Builder(builderClassName = "StoreValueBuilder")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "store"})
@ToString(exclude = { "store" })
@EntityListeners(value = { AuditingEntityListener.class })
@Entity
@Table(name = "STORE_VALUE")
public class StoreValue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STORE_VALUE_ID", unique = true, nullable = false)
	private Long storeValueId;
	
	@Column(name = "STORE_ID", unique = true, nullable = false)
	private Long storeId;
	
	@Column(name = "VALUE", nullable = false)
	private Float value;
	
	@CreatedDate
	@Column(name = "CREATED_AT", nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "MODIFIED_AT", nullable = false)
	private LocalDateTime modifiedAt;
	
	@ManyToOne
	@JoinColumn(name = "STORE_ID", nullable = false, insertable = false, updatable = false)
	private Store store;
	
}