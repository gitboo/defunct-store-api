package defunct.store.core.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QResourceLock is a Querydsl query type for ResourceLock
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QResourceLock extends EntityPathBase<ResourceLock> {

    private static final long serialVersionUID = -517610911L;

    public static final QResourceLock resourceLock = new QResourceLock("resourceLock");

    public final DateTimePath<java.util.Date> lockedAt = createDateTime("lockedAt", java.util.Date.class);

    public final StringPath lockName = createString("lockName");

    public final StringPath owner = createString("owner");

    public final DateTimePath<java.util.Date> unlockAt = createDateTime("unlockAt", java.util.Date.class);

    public final NumberPath<Long> version = createNumber("version", Long.class);

    public QResourceLock(String variable) {
        super(ResourceLock.class, forVariable(variable));
    }

    public QResourceLock(Path<? extends ResourceLock> path) {
        super(path.getType(), path.getMetadata());
    }

    public QResourceLock(PathMetadata metadata) {
        super(ResourceLock.class, metadata);
    }

}

