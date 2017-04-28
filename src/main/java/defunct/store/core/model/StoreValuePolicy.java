package defunct.store.core.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import defunct.store.core.model.type.SectionType;
import defunct.store.core.model.type.SectionTypeConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderClassName = "StoreValuePolicyBuilder")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EntityListeners(value = { AuditingEntityListener.class })
@Entity
@Table(name = "STORE_VALUE_POLICY")
public class StoreValuePolicy {

	@Id
	@Column(name = "SECTION_CODE", unique = true, nullable = false, length = 100)
	private String sectionCode;

	@Column(name = "SECTION", unique = true, nullable = false, length = 100)
	private String section;

	@Column(name = "RATIO", unique = true, nullable = false)
	private Integer ratio;

	@Convert(converter = SectionTypeConverter.class)
	@Column(name = "SECTION_TYPE", unique = true, nullable = false)
	private SectionType sectionType;

	@Column(name = "ROOT_SECTION_CODE", nullable = false, length = 100)
	private String rootSectionCode;

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
		StoreValuePolicy other = (StoreValuePolicy) obj;
		if (sectionCode == null) {
			if (other.sectionCode != null)
				return false;
		} else if (!sectionCode.equals(other.sectionCode))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sectionCode == null) ? 0 : sectionCode.hashCode());
		return result;
	}

}