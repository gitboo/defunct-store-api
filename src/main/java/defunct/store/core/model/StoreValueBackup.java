package defunct.store.core.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderClassName = "StoreValueBuilder")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Entity
@Table(name = "STORE_VALUE_BACKUP")
public class StoreValueBackup {

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoreValueBackup other = (StoreValueBackup) obj;
		if (storeValueId == null) {
			if (other.storeValueId != null)
				return false;
		} else if (!storeValueId.equals(other.storeValueId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((storeValueId == null) ? 0 : storeValueId.hashCode());
		return result;
	}

}