package defunct.store.core.service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDescDto {

	private Long storeDescId;

	private String desciption;

	private LocalDateTime createdAt;

	private LocalDateTime modifiedAt;
}
