package defunct.store.core.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;

import defunct.store.core.model.type.StoreType;
import lombok.Data;

@Data
public class UpdateStoreForm {
	
	@Size(min = 1, max = 100) 
	@NotBlank(message = "storeName must not be balnk")
	private String storeName;

	@NotBlank(message = "phoneNumber must not be balnk")
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 전화번호를 입력해 주세요.")
	private String phoneNumber;

	@NotNull(message = "storeType[S, M, L] must not be null")
	private StoreType storeType;
	
	private String description;

	@JsonIgnore
	public boolean existsDescription() {
		if (Strings.isNullOrEmpty(description)) {
			return false;
		}
		return true;
	}
	@JsonIgnore
	public String toJsonString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}
}
