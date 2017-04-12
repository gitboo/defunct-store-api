package defunct.store.core.error;

public class FailedStoreValueCalculateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FailedStoreValueCalculateException() {
		super();
	}

	public FailedStoreValueCalculateException(String s) {
		super(s);
	}
	

	public FailedStoreValueCalculateException(String s, Throwable t) {
		super(s, t);
	}
}
