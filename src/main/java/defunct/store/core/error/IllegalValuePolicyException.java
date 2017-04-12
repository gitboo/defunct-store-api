package defunct.store.core.error;

public class IllegalValuePolicyException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalValuePolicyException() {
		super();
	}

	public IllegalValuePolicyException(String s) {
		super(s);
	}

	public IllegalValuePolicyException(String s, Throwable t) {
		super(s, t);
	}
}
