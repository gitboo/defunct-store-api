package defunct.store.core.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandlers extends BaseExceptionHandler {
	public ExceptionHandlers() {
		super(log);
		registerMapping(StoreNotFoundException.class, "S0201", "Not Found Store", HttpStatus.NOT_FOUND);
		registerMapping(IllegalValuePolicyException.class, "S0202","The sum of all sections at the same level must be 100.", HttpStatus.FORBIDDEN);		
		registerMapping(FailedStoreValueCalculateException.class, "S0203", "Failed to calculate store value", HttpStatus.INTERNAL_SERVER_ERROR);
		registerMapping(FailedAcquireLockException.class, "S0204", "Failed to acquire lock", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}