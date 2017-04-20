package defunct.store.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import defunct.store.core.model.Store;
import defunct.store.core.model.type.StoreType;
import defunct.store.core.repository.StoreRepository;
import defunct.store.core.service.StoreService;
import defunct.store.core.service.StoreValueService;
import defunct.store.core.service.dto.RangeResult;
import defunct.store.core.service.dto.SimpleStoreDto;
import defunct.store.core.service.dto.StoreDto;
import defunct.store.core.service.dto.StorePhotoDto;
import defunct.store.core.service.dto.UpdateStoreForm;



@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreServiceTest {
	
	private static final Logger log = LoggerFactory.getLogger(StoreServiceTest.class);

	@Autowired
	private StoreService storeService;
	
	@Autowired
	private StoreValueService storeValueService;
	
	@Autowired
	private StoreRepository storeRepository;
	
	
	
	
	@Test
	@Ignore
	@Rollback(false)
	public void updateStoreTest() {
		UpdateStoreForm storeForm = new UpdateStoreForm();
		
		
		Long storeId = 1l;
		storeForm.setDescription("New description2222");
		storeForm.setPhoneNumber("024444-55555");
		storeForm.setStoreType(StoreType.S);
		storeForm.setStoreName("New storeName222");
		
		storeService.updateStore(storeId,storeForm);
	}
	
	@Test
	@Ignore
	@Rollback(true)
	public void storeValueServiceTest() throws Exception {
		storeValueService.estimateStoreValue();
	}
	

	@Test
	@Ignore
	@Transactional(readOnly = true)
	public void showStoreTest() throws Exception {
		
		Long storeId = 1L;
		Store store = storeRepository.findOne(storeId);
		
		StoreDto storeDto = storeService.findStore(storeId);
		log.info(storeDto.toString());
		assertThat(store.getStoreId()).isEqualTo(storeDto.getStoreId());
		assertThat(store.getStoreDescs().size()).isEqualTo(storeDto.getDescription().size());
	}
	
	
	@Test
	@Ignore
	@Transactional(readOnly = true)
	public void showStoresTest() throws Exception {
		
		Pageable pageRequest = new PageRequest(0, 2, new Sort(Sort.Direction.DESC, "value"));
		RangeResult<SimpleStoreDto> results = storeService.findStores(pageRequest);
		log.info(results.toString());
		assertNotNull(results);
	}
	
	
	@Test
	@Ignore
	@Transactional(readOnly = true)
	public void showStorePhotosTest() throws Exception {
		
		Long storeId = 1L;
				Pageable pageRequest = new PageRequest(0, 2, new Sort(Sort.Direction.DESC, "uploadDate"));
		RangeResult<StorePhotoDto> results = storeService.findStorePhotos(storeId, pageRequest);
		log.info(results.toString());
		assertNotNull(results);
	}
}
