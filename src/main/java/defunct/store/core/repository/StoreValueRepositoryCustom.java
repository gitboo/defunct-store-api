package defunct.store.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import defunct.store.core.model.StoreValue;

public interface StoreValueRepositoryCustom {

	Page<StoreValue> search(Pageable pageable);

}
