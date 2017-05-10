package defunct.store.core.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import defunct.store.core.model.type.StoreType;
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

	private LocalDateTime lastUpdateDate;

	private List<StoreDescDto> description;

	private List<StorePhotoDto> photos;

	private StoreValueDto storeValue;

	private LocalDateTime createdAt;

	private LocalDateTime modifiedAt;
}