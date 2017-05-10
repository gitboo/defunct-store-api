package defunct.store.core.service.dto;

import java.time.LocalDateTime;

import defunct.store.core.model.type.StoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleStoreDto {

	private Long storeId;

	private String storeName;

	private long viewCount;

	private long likeCount;

	private boolean onEvent;

	private StoreType storeType;

	private float userAvgScore;

	private StoreValueDto storeValue;

	private LocalDateTime createdAt;

	private LocalDateTime modifiedAt;

}
