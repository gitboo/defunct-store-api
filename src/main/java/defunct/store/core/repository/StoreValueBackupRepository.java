package defunct.store.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import defunct.store.core.model.StoreValueBackup;

@Repository
public interface StoreValueBackupRepository extends JpaRepository<StoreValueBackup, Long> {

}