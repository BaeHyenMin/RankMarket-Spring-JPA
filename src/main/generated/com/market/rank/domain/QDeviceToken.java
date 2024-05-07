package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeviceToken is a Querydsl query type for DeviceToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeviceToken extends EntityPathBase<DeviceToken> {

    private static final long serialVersionUID = -1836920234L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeviceToken deviceToken = new QDeviceToken("deviceToken");

    public final QDeviceToken_DeviceTokenId deviceTokenId;

    public final StringPath token = createString("token");

    public QDeviceToken(String variable) {
        this(DeviceToken.class, forVariable(variable), INITS);
    }

    public QDeviceToken(Path<? extends DeviceToken> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeviceToken(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeviceToken(PathMetadata metadata, PathInits inits) {
        this(DeviceToken.class, metadata, inits);
    }

    public QDeviceToken(Class<? extends DeviceToken> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deviceTokenId = inits.isInitialized("deviceTokenId") ? new QDeviceToken_DeviceTokenId(forProperty("deviceTokenId"), inits.get("deviceTokenId")) : null;
    }

}

