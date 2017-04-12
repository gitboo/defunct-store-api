
package defunct.store.core.service;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionalCommandService {

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T> T doInNewTransaction(Callable<T> callable) {
		try {
			return callable.call();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
