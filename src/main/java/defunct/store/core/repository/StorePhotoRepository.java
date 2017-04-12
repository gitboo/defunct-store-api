package defunct.store.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import defunct.store.core.model.StorePhoto;

@Repository
public interface StorePhotoRepository extends JpaRepository<StorePhoto, Long> {

	Page<StorePhoto> findByStoreId(Long storeId, Pageable pageable);

	
}