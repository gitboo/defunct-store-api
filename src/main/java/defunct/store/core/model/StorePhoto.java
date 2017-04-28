package defunct.store.core.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import defunct.store.core.model.type.PhotoSourceType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@Builder(builderClassName = "StorePhotoBuilder")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "store"})
@ToString(exclude = { "store" })
@EntityListeners(value = { AuditingEntityListener.class })
@Entity
@Table(name = "STORE_PHOTO")
public class StorePhoto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "STORE_PHOTO_ID", unique = true, nullable = false)
	private Long storePhotoId;
	
	@Column(name = "STORE_ID", unique = true, nullable = false)
	private Long storeId;
	
	@Column(name = "PHOTO_URL", nullable = false)
	private String photoUrl;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "SOURCE", nullable = false)
	private PhotoSourceType source;
	
	@Column(name = "UPLOAD_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;
	
	@CreatedDate
	@Column(name = "CREATED_AT", nullable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "MODIFIED_AT", nullable = false)
	private LocalDateTime modifiedAt;
	
	@ManyToOne
	@JoinColumn(name = "STORE_ID", nullable = false, insertable = false, updatable = false)
	private Store store;
	
}