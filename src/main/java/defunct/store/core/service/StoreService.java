package defunct.store.core.service;

import org.springframework.data.domain.Pageable;

import defunct.store.core.service.dto.RangeResult;
import defunct.store.core.service.dto.SimpleStoreDto;
import defunct.store.core.service.dto.StoreDto;
import defunct.store.core.service.dto.StorePhotoDto;
import defunct.store.core.service.dto.UpdateStoreForm;

public interface StoreService {

	StoreDto findStore(Long storeId);

	RangeResult<SimpleStoreDto> findStores(Pageable pageable);
	
	RangeResult<StorePhotoDto> findStorePhotos(Long storeId, Pageable pageable);
	
	StoreDto updateStore(Long storeId, UpdateStoreForm storeForm);

}
