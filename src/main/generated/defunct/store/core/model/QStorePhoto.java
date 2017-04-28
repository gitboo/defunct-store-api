package defunct.store.core.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStorePhoto is a Querydsl query type for StorePhoto
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStorePhoto extends EntityPathBase<StorePhoto> {

    private static final long serialVersionUID = 1823782873L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStorePhoto storePhoto = new QStorePhoto("storePhoto");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final StringPath photoUrl = createString("photoUrl");

    public final EnumPath<defunct.store.core.model.type.PhotoSourceType> source = createEnum("source", defunct.store.core.model.type.PhotoSourceType.class);

    public final QStore store;

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    public final NumberPath<Long> storePhotoId = createNumber("storePhotoId", Long.class);

    public final DateTimePath<java.util.Date> uploadDate = createDateTime("uploadDate", java.util.Date.class);

    public QStorePhoto(String variable) {
        this(StorePhoto.class, forVariable(variable), INITS);
    }

    public QStorePhoto(Path<? extends StorePhoto> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStorePhoto(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStorePhoto(PathMetadata metadata, PathInits inits) {
        this(StorePhoto.class, metadata, inits);
    }

    public QStorePhoto(Class<? extends StorePhoto> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store")) : null;
    }

}

