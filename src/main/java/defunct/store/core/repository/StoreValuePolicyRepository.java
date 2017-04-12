package defunct.store.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import defunct.store.core.model.StoreValuePolicy;

@Repository
public interface StoreValuePolicyRepository extends JpaRepository<StoreValuePolicy, String> {

}