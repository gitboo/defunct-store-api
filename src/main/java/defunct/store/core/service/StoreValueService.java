package defunct.store.core.service;

import defunct.store.core.service.dto.SimpleResult;
import defunct.store.core.service.dto.StoreValuePolicySection;

public interface StoreValueService {

	StoreValuePolicySection updateStoreValuePolicy(StoreValuePolicySection storeValuePolicySection);

	SimpleResult estimateStoreValue();

}
