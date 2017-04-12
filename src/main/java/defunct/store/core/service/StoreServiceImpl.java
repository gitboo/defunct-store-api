package defunct.store.core.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import defunct.store.core.error.StoreNotFoundException;
import defunct.store.core.model.Store;
import defunct.store.core.model.StoreDesc;
import defunct.store.core.model.StorePhoto;
import defunct.store.core.model.StoreValue;
import defunct.store.core.repository.StorePhotoRepository;
import defunct.store.core.repository.StoreRepository;
import defunct.store.core.repository.StoreValueRepository;
import defunct.store.core.service.dto.RangeResult;
import defunct.store.core.service.dto.SimpleStoreDto;
import defunct.store.core.service.dto.StoreDto;
import defunct.store.core.service.dto.StorePhotoDto;
import defunct.store.core.service.dto.StoreValueDto;
import defunct.store.core.service.dto.UpdateStoreForm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StoreServiceImpl implements StoreService {
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private StorePhotoRepository storePhotoRepository;
	
	@Autowired
	private StoreValueRepository storeValueRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	@Transactional
	public StoreDto findStore(Long storeId) {
		checkNotNull(storeId, "The storeId must not be null");

		Store store = storeRepository.findOne(storeId);
		if (store == null) {
			throw new StoreNotFoundException(storeId);
		}
		store.updateViewCount();
		log.info("Found,  {}", store);
		return modelMapper.map(store, StoreDto.class);
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public RangeResult<SimpleStoreDto> findStores(Pageable pageable) {
		checkNotNull(pageable);
		
		Page<StoreValue> storePage = storeValueRepository.search(pageable);
		
		Function<StoreValue, SimpleStoreDto> mapperFunction = (storeValue) -> {
			StoreValueDto storeValueDto = modelMapper.map(storeValue, StoreValueDto.class);
			SimpleStoreDto simpleStoreDto = modelMapper.map(storeValue.getStore(), SimpleStoreDto.class);
			simpleStoreDto.setStoreValue(storeValueDto);
			return simpleStoreDto;
		};
		return RangeResult.create(storePage, mapperFunction);
	}
	

	@Override
	@Transactional
	public StoreDto updateStore(Long storeId, UpdateStoreForm storeForm) {
		checkNotNull(storeId);
		checkNotNull(storeForm);
		
		Store store = storeRepository.findOne(storeId);
		if (store == null) {
			throw new StoreNotFoundException(storeId);
		}
		log.info("Found Store[{}]", store.toString());
		
		store.setStoreType(storeForm.getStoreType());
		store.setStoreName(storeForm.getStoreName());
		store.setPhoneNumber(storeForm.getPhoneNumber());
		
		/**
		 * 상점 설명의 경우 1:N 의 경우 이지만 한개의 record만 사용한다고 가정 한다.
		 *  그래서 기존 상점 설명을 삭제 하고 새로운 상점설명을 입력 한다.
		 */
		if (storeForm.existsDescription()) {
			store.removeAllStoreDesc();
			store.addStoreDesc(StoreDesc.builder()
					.desciption(storeForm.getDescription())
					.storeId(storeId)
					.build());
		}
		store.updateLastUpdateDate();
		return modelMapper.map(store, StoreDto.class);
	}
	


	@Override
	@Transactional(readOnly = true)
	public RangeResult<StorePhotoDto> findStorePhotos(Long storeId, Pageable pageable) {
		checkNotNull(storeId);
		checkNotNull(pageable);
		
		Store store = storeRepository.findOne(storeId);
		if (store == null) {
			throw new StoreNotFoundException(storeId);
		}
		Page<StorePhoto> page = storePhotoRepository.findByStoreId(storeId, pageable);
		Function<StorePhoto, StorePhotoDto> mapperFunction = storePhoto -> modelMapper.map(storePhoto, StorePhotoDto.class);

		return RangeResult.create(page, mapperFunction);
	}
}
