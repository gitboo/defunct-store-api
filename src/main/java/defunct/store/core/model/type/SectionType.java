package defunct.store.core.model.type;

public enum SectionType {
	SUB("S"), ROOT("R");

	private String code;

	private SectionType(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public static SectionType codeOf(String value) {
		for (SectionType each : values()) {
			if (each.getCode().equals(value)) {
				return each;
			}
		}
		throw new IllegalArgumentException("Unknown value: " + value);
	}
}
