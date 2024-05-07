package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDealDtm_DealDtmId is a Querydsl query type for DealDtmId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QDealDtm_DealDtmId extends BeanPath<DealDtm.DealDtmId> {

    private static final long serialVersionUID = -1662282334L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDealDtm_DealDtmId dealDtmId = new QDealDtm_DealDtmId("dealDtmId");

    public final QProduct prd;

    public QDealDtm_DealDtmId(String variable) {
        this(DealDtm.DealDtmId.class, forVariable(variable), INITS);
    }

    public QDealDtm_DealDtmId(Path<? extends DealDtm.DealDtmId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDealDtm_DealDtmId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDealDtm_DealDtmId(PathMetadata metadata, PathInits inits) {
        this(DealDtm.DealDtmId.class, metadata, inits);
    }

    public QDealDtm_DealDtmId(Class<? extends DealDtm.DealDtmId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.prd = inits.isInitialized("prd") ? new QProduct(forProperty("prd"), inits.get("prd")) : null;
    }

}

