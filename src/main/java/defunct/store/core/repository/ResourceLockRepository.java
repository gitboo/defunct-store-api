
package defunct.store.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import defunct.store.core.model.ResourceLock;

public interface ResourceLockRepository extends JpaRepository<ResourceLock, String> {

}
