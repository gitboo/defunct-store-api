package defunct.store.core.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import defunct.store.core.model.ResourceLock;
import defunct.store.core.repository.ResourceLockRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ResourceLockService {
	
	private static final long DEFAULT_LOCK_DURATION_IN_MILLIS = 600000L;
	
	@Autowired
	private ResourceLockRepository lockRepository;
	
	@Autowired
	private TransactionalCommandService commandService;
	
	
	
	private String owner;
	
	@Value("${lock.duration.millis}")
	private Long duration;		// Lock 소유시간 (ms 단위)
	
	
	@PostConstruct
	public void init() {
		try {
			this.owner = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			log.error("Fail to determine hostname", e);
			throw new RuntimeException(e);
		}
		if (duration == null) {
			log.info("Not Found ${lock.duration.millis} : will set the default value[{}]",
					DEFAULT_LOCK_DURATION_IN_MILLIS);
			duration = DEFAULT_LOCK_DURATION_IN_MILLIS;
		}
		log.info("LockService initialized: owner={}, duration={}", owner, duration);
	}
	
	
	public boolean tryLock(final String lockName) {
		checkNotNull(lockName);
		
		log.info("Try to acquire Lock [{}]", lockName);
		
		for (int i = 0; i < 3; i++) {
			try {
				return commandService.doInNewTransaction(new Callable<Boolean>() {
	
					@Override
					public Boolean call() throws Exception {
						ResourceLock lock = lockRepository.findOne(lockName);
						if (lock == null) {
							lock = new ResourceLock(lockName, owner, duration);
						}
						if (lock.tryAcquire(owner, duration)) {
							lockRepository.saveAndFlush(lock);
							log.info("Lock [{}] acquired", lockName);
							return true;
						}
						else {
							log.info("Lock [{}] is possessed by other process", lockName);
							return false;
						}
					}
				});
			}
			catch (Exception e) {
				if (e instanceof ObjectOptimisticLockingFailureException) {
					log.info("Lock [{}] acquisition failed - retry...", lockName);
					continue;	// 재시도
				}
				
				log.info("Lock [{}] acquisition failed", lockName, e);
				return false;
			}
		}
		return false;
	}
	
	public boolean unLock(final String lockName) {
		checkNotNull(lockName);
		log.info("Try to Unlock [{}]", lockName);
		ResourceLock lock = lockRepository.findOne(lockName);
		if (lock == null) {
			return true;
		}
		lock.setUnlockAt(new Date());
		lockRepository.saveAndFlush(lock);
		return true;
	}

	public long getLockDurationMillis() {
		return TimeUnit.SECONDS.toMillis(duration);
	}
}