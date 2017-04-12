package defunct.store.core.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StoreEntityManager {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public void truncateStoreValueBackup() {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("TRUNCATE TABLE STORE_VALUE_BACKUP").executeUpdate();
		em.getTransaction().commit();
	}

	public void switchStoreValue() {
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("ALTER TABLE STORE_VALUE RENAME TO STORE_VALUE_OLD").executeUpdate();
		em.createNativeQuery("ALTER TABLE STORE_VALUE_BACKUP RENAME TO STORE_VALUE").executeUpdate();
		em.createNativeQuery("ALTER TABLE STORE_VALUE_OLD RENAME TO STORE_VALUE_BACKUP").executeUpdate();
		em.getTransaction().commit();
	}
}
