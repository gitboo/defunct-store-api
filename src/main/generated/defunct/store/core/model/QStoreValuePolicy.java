package defunct.store.core.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStoreValuePolicy is a Querydsl query type for StoreValuePolicy
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStoreValuePolicy extends EntityPathBase<StoreValuePolicy> {

    private static final long serialVersionUID = 2038033002L;

    public static final QStoreValuePolicy storeValuePolicy = new QStoreValuePolicy("storeValuePolicy");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedAt = createDateTime("modifiedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> ratio = createNumber("ratio", Integer.class);

    public final StringPath rootSectionCode = createString("rootSectionCode");

    public final StringPath section = createString("section");

    public final StringPath sectionCode = createString("sectionCode");

    public final EnumPath<defunct.store.core.model.type.SectionType> sectionType = createEnum("sectionType", defunct.store.core.model.type.SectionType.class);

    public QStoreValuePolicy(String variable) {
        super(StoreValuePolicy.class, forVariable(variable));
    }

    public QStoreValuePolicy(Path<? extends StoreValuePolicy> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStoreValuePolicy(PathMetadata metadata) {
        super(StoreValuePolicy.class, metadata);
    }

}

