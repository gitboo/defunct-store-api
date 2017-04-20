package defunct.store.core.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStoreValueBackup is a Querydsl query type for StoreValueBackup
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStoreValueBackup extends EntityPathBase<StoreValueBackup> {

    private static final long serialVersionUID = 1624029946L;

    public static final QStoreValueBackup storeValueBackup = new QStoreValueBackup("storeValueBackup");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> storeId = createNumber("storeId", Long.class);

    public final NumberPath<Long> storeValueId = createNumber("storeValueId", Long.class);

    public final NumberPath<Float> value = createNumber("value", Float.class);

    public QStoreValueBackup(String variable) {
        super(StoreValueBackup.class, forVariable(variable));
    }

    public QStoreValueBackup(Path<? extends StoreValueBackup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStoreValueBackup(PathMetadata metadata) {
        super(StoreValueBackup.class, metadata);
    }

}

