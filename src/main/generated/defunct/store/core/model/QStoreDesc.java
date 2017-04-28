package defunct.store.core.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStoreDesc is a Querydsl query type for StoreDesc
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStoreDesc extends EntityPathBase<StoreDesc> {

    private static final long serialVersionUID = 58471434L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStoreDesc storeDesc = new QStoreDesc("storeDesc");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath desciption = createString("desciption");

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final QStore store;

    public final NumberPath<Long> storeDescId = createNumber("storeDescId", Long.class);

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    public QStoreDesc(String variable) {
        this(StoreDesc.class, forVariable(variable), INITS);
    }

    public QStoreDesc(Path<? extends StoreDesc> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStoreDesc(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStoreDesc(PathMetadata metadata, PathInits inits) {
        this(StoreDesc.class, metadata, inits);
    }

    public QStoreDesc(Class<? extends StoreDesc> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.store = inits.isInitialized("store") ? new QStore(forProperty("store")) : null;
    }

}

