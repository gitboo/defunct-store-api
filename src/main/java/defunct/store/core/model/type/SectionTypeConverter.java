package defunct.store.core.model.type;

import javax.persistence.AttributeConverter;

public class SectionTypeConverter implements AttributeConverter<SectionType, String> {

	@Override
	public String convertToDatabaseColumn(SectionType attribute) {
		return attribute.getCode();
	}

	@Override
	public SectionType convertToEntityAttribute(String dbData) {
		return SectionType.codeOf(dbData);
	}

}
