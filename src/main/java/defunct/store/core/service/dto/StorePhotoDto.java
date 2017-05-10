package defunct.store.core.service.dto;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import defunct.store.core.model.type.PhotoSourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorePhotoDto {

	private Long storePhotoId;

	private String photoUrl;

	private PhotoSourceType source;

	private LocalDateTime uploadDate;

	private LocalDateTime createdAt;

	private LocalDateTime modifiedAt;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);

	}
}
