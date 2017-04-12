package defunct.store.core.service.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import defunct.store.web.support.ISO8601DateSerializer;
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

	@JsonSerialize(using = ISO8601DateSerializer.class)
	private LocalDateTime createdAt;

	@JsonSerialize(using = ISO8601DateSerializer.class)
	private LocalDateTime modifiedAt;
}
