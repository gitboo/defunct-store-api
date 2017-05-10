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
public class StoreValueDto {

	private Float value;

	private LocalDateTime createdAt;

	private LocalDateTime modifiedAt;

}
