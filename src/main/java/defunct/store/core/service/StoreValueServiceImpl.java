package defunct.store.core.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Stopwatch;

import defunct.store.core.error.FailedAcquireLockException;
import defunct.store.core.model.Store;
import defunct.store.core.model.StoreValueBackup;
import defunct.store.core.model.StoreValuePolicy;
import defunct.store.core.repository.StoreEntityManager;
import defunct.store.core.repository.StoreRepository;
import defunct.store.core.repository.StoreValueBackupRepository;
import defunct.store.core.repository.StoreValuePolicyRepository;
import defunct.store.core.service.calculator.StoreValeCalculator;
import defunct.store.core.service.dto.PolicySection;
import defunct.store.core.service.dto.SimpleResult;
import defunct.store.core.service.dto.StoreValuePolicySection;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
public class StoreValueServiceImpl implements StoreValueService {
	
	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
	private StoreValueBackupRepository storeValueBackupRepository;
	
	@Autowired
	private StoreValuePolicyRepository storeValuePolicyRepository;
	
	@Autowired
	private StoreValeCalculator calculator;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private StoreEntityManager storeEntityManager;
	
	@Autowired
	private ResourceLockService lockService;
	
	
	/**
	 * -상점 가치 산정 규칙을 변경한다.
	 * -대분류는 RootSection 측정항목은 SubSection 으로 명칭 했다.
	 */
	@Override
	@Transactional
	public StoreValuePolicySection updateStoreValuePolicy(@Valid StoreValuePolicySection storeValuePolicySection) {
		checkNotNull(storeValuePolicySection);
		
		/*
		 * 가치 대분류 의 합이 반영비율의 합이 100 그리고 대분류의 각 측정항목의 비율이 100인지를 검증한다.
		 */
		storeValuePolicySection.checkValidStoreValuePolicy();
		// 기존 규칙을 전부 삭제한다.
		storeValuePolicyRepository.deleteAll();
		log.info("Delete all storeValuePolicy");
		
		// 새 규칙을 입력한다.
		for (PolicySection each : storeValuePolicySection.getSections()) {
			StoreValuePolicy storeValuePolicy = modelMapper.map(each, StoreValuePolicy.class);
			storeValuePolicyRepository.save(storeValuePolicy);			
			log.info("Saved [{}]", storeValuePolicy);
		}
		return storeValuePolicySection;
	}
	

	/**
	 * -상점 가치 산정을 수행한다.
	 * -대분류는 RootSection 측정항목은 SubSection 으로 명칭 했다.
	 */
	@Override
	@Transactional
	public SimpleResult estimateStoreValue() {
		
		// 수행 시간이 오래 걸릴수 있으므로 테이블 락을 건다.
		if (!lockService.tryLock(LockName.ESTIMATE_STOREVALUE)) {
			throw new FailedAcquireLockException("Please try again for a while.");
		}
		// 수행 시간을 측정한다.
		Stopwatch stopwatch = Stopwatch.createStarted();
		
		// 상점 가치 산정의 백업 테이블을 truncate 한다.
		// - truncate 를 하는 이유는 속도가 빠르며 index 사이즈를 리셋 하기 위해서 이다. 
		storeEntityManager.truncateStoreValueBackup();
		
		// 상점 정보와 상정 규칙 정보를 가져온다.
		List<Store> stores = storeRepository.findAll();
		List<StoreValuePolicy> policys = storeValuePolicyRepository.findAll();
		
		
		
		// 계산된 상점의  카운트 변수
		long calcCount = 0;
		// 상점 리스트의 반복문을 돌면서 계산을 수행한다.
		outer:
		for (Store store : stores) {
			if (stopwatch.elapsed(TimeUnit.MILLISECONDS) >= lockService.getLockDurationMillis()) {
				boolean locked = lockService.tryLock(LockName.ESTIMATE_STOREVALUE);
				stopwatch.reset();
				stopwatch.start();
				if (!locked) {
					log.info("Fail to acquire Lock: {}, stop processing...", LockName.ESTIMATE_STOREVALUE);
					break outer;
				}
			}
			float storeValue = calculator.calculate(store, policys);
			storeValueBackupRepository.save(StoreValueBackup.builder()
					.storeId(store.getStoreId())
					.value(storeValue).build());
			
			/**
			 * 1000 개 계산 할 때마다 flush 를 해준다.  
			 */
			if (++calcCount % 1000 == 0) {
				storeValueBackupRepository.flush();
			}
		}
		
		
		/*산정된 상점 가치 값은  STORE_VALUE_BACKUP 테이블에 입력되며 
		 * 계산이 완료되면 원래의 상점 가치값이 저장된 STORE_VALUE 와 rename table을 사용하여
		 * 바꿔치기 한다.
		 */	
		storeEntityManager.switchStoreValue();
		lockService.unLock(LockName.ESTIMATE_STOREVALUE);
		return new SimpleResult(true, "The store values calculation has been completed!! [" + calcCount + "]");
	}
	
	private static class LockName {
		static final String ESTIMATE_STOREVALUE = "EstimateStoreValue";
	}
}