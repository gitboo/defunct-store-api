package defunct.store.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.google.common.collect.Lists;

import defunct.store.core.model.type.StoreType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Data
@Builder(builderClassName = "StoreBuilder")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "storePhotos", "storeDescs", "storeValues" })
@ToString(exclude = { "storePhotos", "storeDescs", "storeValues" })
@Entity
@Table(name = "STORE")
public class Store  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STORE_ID", unique = true, nullable = false)
	private Long storeId;

	@Column(name = "STORE_NAME", length = 100)
	private String storeName;
	
	@Column(name = "PHONE_NUMBER", length = 20)
	private String phoneNumber;
	
	@Column(name = "VIEW_COUNT", nullable = false)
	private Long viewCount;
	
	@Column(name = "LIKE_COUNT", nullable = false)
	private Long likeCount;
	
	@Type(type = "yes_no")
	@Column(name = "ON_EVENT", nullable = false)
	private Boolean onEvent;
	
	@Column(name = "USER_AVG_SCORE", nullable = false)
	private Float userAvgScore;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STORE_TYPE", nullable = false)
	private StoreType storeType;
	
	@Column(name = "LAST_UPDATE_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDate;
	
	@CreatedDate
	@Column(name = "CREATED_AT", nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "MODIFIED_AT", nullable = false)
	private LocalDateTime modifiedAt;
	
	@OneToMany(mappedBy = "store", cascade = { CascadeType.PERSIST }, orphanRemoval = true)
	private List<StorePhoto> storePhotos = Lists.newArrayList();
	
	@OneToMany(mappedBy = "store", cascade = { CascadeType.PERSIST }, orphanRemoval = true)
	private List<StoreDesc> storeDescs = Lists.newArrayList();
	
	@OneToMany(mappedBy = "store", cascade = { CascadeType.PERSIST }, orphanRemoval = true)
	private List<StoreValue> storeValues = Lists.newArrayList();
	
	
	public void updateLastUpdateDate() {
		lastUpdateDate = new Date();
	}
	
	public StoreDesc addStoreDesc(StoreDesc storeDesc) {
		getStoreDescs().add(storeDesc);
		storeDesc.setStore(this);
		return storeDesc;
	}
	
	public StoreDesc removeStoreDesc(StoreDesc storeDesc) {
		getStoreDescs().remove(storeDesc);
		storeDesc.setStore(null);
		return storeDesc;
	}
	
	public void removeAllStoreDesc() {
		for (Iterator<StoreDesc> iterator = getStoreDescs().iterator(); iterator.hasNext();) {
			StoreDesc each = iterator.next();
			log.info("removed ", each);
			each.setStore(null);
			iterator.remove();
		}
	}

	public static class StoreBuilder {
		private Long viewCount = 0L;
		private Long likeCount = 0L;
		private Float userAvgScore = 0f;
		private Boolean onEvent = false;
	}
	
	public StoreValue getStoreValue() {
		if (storeValues.isEmpty()) {
			return null;
		}
		return storeValues.get(0);
	}
	
	public StoreDesc getStoreDesc() {
		if (storeDescs.isEmpty()) {
			return null;
		}
		return storeDescs.get(0);
	}

	public void updateViewCount() {
		setViewCount(getViewCount() + 1);	
	}	
}