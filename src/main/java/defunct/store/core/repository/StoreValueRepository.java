package defunct.store.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import defunct.store.core.model.StoreValue;

@Repository
public interface StoreValueRepository extends JpaRepository<StoreValue, Long>, StoreValueRepositoryCustom {

}