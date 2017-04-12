package defunct.store.core.error;

public class StoreNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StoreNotFoundException() {
		super();
	}

	public StoreNotFoundException(String s) {
		super(s);
	}
	
	public StoreNotFoundException(Long storeId) {
		super("Not Found store[ " + storeId + " ]");
	}

	public StoreNotFoundException(String s, Throwable t) {
		super(s, t);
	}
}
