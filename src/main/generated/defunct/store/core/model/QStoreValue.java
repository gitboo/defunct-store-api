package defunct.store.core.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoreValue is a Querydsl query type for StoreValue
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStoreValue extends EntityPathBase<StoreValue> {

    private static final long serialVersionUID = 1829112600L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoreValue storeValue = new QStoreValue("storeValue");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final QStore store;

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    public final NumberPath<Long> storeValueId = createNumber("storeValueId", Long.class);

    public final NumberPath<Float> value = createNumber("value", Float.class);

    public QStoreValue(String variable) {
        this(StoreValue.class, forVariable(variable), INITS);
    }

    public QStoreValue(Path<? extends StoreValue> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoreValue(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoreValue(PathMetadata metadata, PathInits inits) {
        this(StoreValue.class, metadata, inits);
    }

    public QStoreValue(Class<? extends StoreValue> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store")) : null;
    }

}

