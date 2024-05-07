package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeviceToken_DeviceTokenId is a Querydsl query type for DeviceTokenId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDeviceToken_DeviceTokenId extends BeanPath<DeviceToken.DeviceTokenId> {

    private static final long serialVersionUID = 1920951302L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeviceToken_DeviceTokenId deviceTokenId = new QDeviceToken_DeviceTokenId("deviceTokenId");

    public final QUsr usr;

    public QDeviceToken_DeviceTokenId(String variable) {
        this(DeviceToken.DeviceTokenId.class, forVariable(variable), INITS);
    }

    public QDeviceToken_DeviceTokenId(Path<? extends DeviceToken.DeviceTokenId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeviceToken_DeviceTokenId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeviceToken_DeviceTokenId(PathMetadata metadata, PathInits inits) {
        this(DeviceToken.DeviceTokenId.class, metadata, inits);
    }

    public QDeviceToken_DeviceTokenId(Class<? extends DeviceToken.DeviceTokenId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.usr = inits.isInitialized("usr") ? new QUsr(forProperty("usr")) : null;
    }

}

