package defunct.store.core.service.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import defunct.store.core.model.type.SectionType;
import lombok.Data;

@Data
public class PolicySection {

	@NotBlank
	private String sectionCode;

	@NotBlank
	private String section;

	@NotNull
	private Integer ratio;

	@NotNull
	private SectionType sectionType;

	@NotBlank
	private String rootSectionCode;
}
