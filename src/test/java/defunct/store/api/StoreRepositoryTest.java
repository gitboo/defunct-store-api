package defunct.store.api;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import defunct.store.core.model.Store;
import defunct.store.core.model.StoreDesc;
import defunct.store.core.model.StoreValue;
import defunct.store.core.model.type.StoreType;
import defunct.store.core.repository.StoreRepository;
import defunct.store.core.repository.StoreValueRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoreRepositoryTest {

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private StoreValueRepository storeValueRepository;

	@Test
	@Ignore
	@Transactional
	@Rollback(false)
	public void findStoreValues() {

		Pageable pageRequest = new PageRequest(0, 2, new Sort(Direction.DESC, "value"));
		Page<StoreValue> stores = storeValueRepository.findAll(pageRequest);
		assertNotNull(stores);
	}

	@Test
	@Ignore
	@Transactional
	@Rollback(false)
	public void findStore() {
		Store store = storeRepository.findOne(1l);

		store.addStoreDesc(StoreDesc.builder().storeId(store.getStoreId())
				.desciption("desciption desciption desciption desciption").build());
		assertNotNull(store);
	}

	@Test
	@Ignore
	@Transactional
	@Rollback(false)
	public void findStoreValue() {
		StoreValue storeValue = storeValueRepository.findOne(1l);
		assertNotNull(storeValue.getStore());
	}

	@Test
	@Ignore
	public void createStore() {

		Store store = storeRepository.save(Store.builder().lastUpdateDate(new Date()).storeType(StoreType.M)
				.phoneNumber("000-0000-0000").storeName("Test6").build());
		assertNotNull(store);
	}
}