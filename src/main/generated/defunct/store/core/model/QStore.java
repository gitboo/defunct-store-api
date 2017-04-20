package defunct.store.core.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStore is a Querydsl query type for Store
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStore extends EntityPathBase<Store> {

    private static final long serialVersionUID = 2006734297L;

    public static final QStore store = new QStore("store");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.util.Date> lastUpdateDate = createDateTime("lastUpdateDate", java.util.Date.class);

    public final NumberPath<Long> likeCount = createNumber("likeCount", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final BooleanPath onEvent = createBoolean("onEvent");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<StoreDesc, QStoreDesc> storeDescs = this.<StoreDesc, QStoreDesc>createList("storeDescs", StoreDesc.class, QStoreDesc.class, PathInits.DIRECT2);

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    public final StringPath storeName = createString("storeName");

    public final ListPath<StorePhoto, QStorePhoto> storePhotos = this.<StorePhoto, QStorePhoto>createList("storePhotos", StorePhoto.class, QStorePhoto.class, PathInits.DIRECT2);

    public final EnumPath<defunct.store.core.model.type.StoreType> storeType = createEnum("storeType", defunct.store.core.model.type.StoreType.class);

    public final ListPath<StoreValue, QStoreValue> storeValues = this.<StoreValue, QStoreValue>createList("storeValues", StoreValue.class, QStoreValue.class, PathInits.DIRECT2);

    public final NumberPath<Float> userAvgScore = createNumber("userAvgScore", Float.class);

    public final NumberPath<Long> viewCount = createNumber("viewCount", Long.class);

    public QStore(String variable) {
        super(Store.class, forVariable(variable));
    }

    public QStore(Path<? extends Store> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStore(PathMetadata metadata) {
        super(Store.class, metadata);
    }

}

