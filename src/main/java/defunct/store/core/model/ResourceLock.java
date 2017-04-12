
package defunct.store.core.model;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "RESOURCE_LOCK")
public class ResourceLock implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LOCK_NAME", nullable = false)
	private String lockName;

	@Column(name = "OWNER", nullable = false)
	private String owner;

	@Column(name = "LOCKED_AT", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lockedAt;

	@Column(name = "UNLOCK_AT", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date unlockAt;

	@Version
	private long version; // Optimistic Locking 사용

	public ResourceLock(String lockName, String owner, long duration) {
		this.lockName = checkNotNull(lockName);
		this.owner = checkNotNull(owner);
		Date now = new Date();
		this.lockedAt = now;
		this.unlockAt = new Date(now.getTime() + duration);
	}

	public boolean tryAcquire(String owner, long duration) {
		Date now = new Date();
		if (this.unlockAt.before(now)) {
			this.owner = owner;
			this.lockedAt = now;
			this.unlockAt = new Date(now.getTime() + duration);
			return true;
		}
		return false;
	}
}