package defunct.store.core.error;

public class FailedAcquireLockException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FailedAcquireLockException() {
		super();
	}

	public FailedAcquireLockException(String s) {
		super(s);
	}
	

	public FailedAcquireLockException(String s, Throwable t) {
		super(s, t);
	}
}
