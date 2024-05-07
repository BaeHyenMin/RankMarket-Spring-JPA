package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPay_PayListsId is a Querydsl query type for PayListsId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QPay_PayListsId extends BeanPath<Pay.PayListsId> {

    private static final long serialVersionUID = -1686083333L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPay_PayListsId payListsId = new QPay_PayListsId("payListsId");

    public final QProduct prd;

    public final QUsr usr;

    public QPay_PayListsId(String variable) {
        this(Pay.PayListsId.class, forVariable(variable), INITS);
    }

    public QPay_PayListsId(Path<? extends Pay.PayListsId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPay_PayListsId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPay_PayListsId(PathMetadata metadata, PathInits inits) {
        this(Pay.PayListsId.class, metadata, inits);
    }

    public QPay_PayListsId(Class<? extends Pay.PayListsId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.prd = inits.isInitialized("prd") ? new QProduct(forProperty("prd"), inits.get("prd")) : null;
        this.usr = inits.isInitialized("usr") ? new QUsr(forProperty("usr")) : null;
    }

}

