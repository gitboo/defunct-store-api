package defunct.store.core.service.dto;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import defunct.store.core.model.type.PhotoSourceType;
import defunct.store.web.support.ISO8601DateSerializer;
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
	
	@JsonSerialize(using = ISO8601DateSerializer.class)
	private LocalDateTime uploadDate;
	
	@JsonSerialize(using = ISO8601DateSerializer.class)
	private LocalDateTime createdAt;

	@JsonSerialize(using = ISO8601DateSerializer.class)
	private LocalDateTime modifiedAt;
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	
	}
}
