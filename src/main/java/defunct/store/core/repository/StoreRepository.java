package defunct.store.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import defunct.store.core.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

}