package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDealDtm is a Querydsl query type for DealDtm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDealDtm extends EntityPathBase<DealDtm> {

    private static final long serialVersionUID = 1404161124L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDealDtm dealDtm = new QDealDtm("dealDtm");

    public final QDealDtm_DealDtmId dealDtmId;

    public final DateTimePath<java.util.Date> dlTime = createDateTime("dlTime", java.util.Date.class);

    public final StringPath lat = createString("lat");

    public final StringPath lng = createString("lng");

    public final QUsr usr;

    public QDealDtm(String variable) {
        this(DealDtm.class, forVariable(variable), INITS);
    }

    public QDealDtm(Path<? extends DealDtm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDealDtm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDealDtm(PathMetadata metadata, PathInits inits) {
        this(DealDtm.class, metadata, inits);
    }

    public QDealDtm(Class<? extends DealDtm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dealDtmId = inits.isInitialized("dealDtmId") ? new QDealDtm_DealDtmId(forProperty("dealDtmId"), inits.get("dealDtmId")) : null;
        this.usr = inits.isInitialized("usr") ? new QUsr(forProperty("usr")) : null;
    }

}

