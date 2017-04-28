package defunct.store.core.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import defunct.store.core.model.type.StoreType;
import defunct.store.web.support.ISO8601DateSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {

	@ApiModelProperty(notes = "id of store", required = true)
	private Long storeId;

	@ApiModelProperty(notes = "The name of the store", required = true)
	private String storeName;

	private String phoneNumber;

	private long viewCount;

	private long likeCount;

	private boolean onEvent;

	private float userAvgScore;

	private StoreType storeType;

	@JsonSerialize(using = ISO8601DateSerializer.class)
	private LocalDateTime lastUpdateDate;

	private List<StoreDescDto> description;

	private List<StorePhotoDto> photos;

	private StoreValueDto storeValue;

	@JsonSerialize(using = ISO8601DateSerializer.class)
	private LocalDateTime createdAt;

	@JsonSerialize(using = ISO8601DateSerializer.class)
	private LocalDateTime modifiedAt;
}