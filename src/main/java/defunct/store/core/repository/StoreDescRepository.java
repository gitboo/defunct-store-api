package defunct.store.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import defunct.store.core.model.StoreDesc;

@Repository
public interface StoreDescRepository extends JpaRepository<StoreDesc, Long> {

}