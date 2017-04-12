package defunct.store.core.service.calculator;

import defunct.store.core.model.Store;
import defunct.store.core.model.StoreValuePolicy;

public interface StoreValueSectionCalculate {
	public float calculate(Store store, StoreValuePolicy storeValuePolicy);
}
