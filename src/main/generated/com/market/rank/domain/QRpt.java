package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRpt is a Querydsl query type for Rpt
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRpt extends EntityPathBase<Rpt> {

    private static final long serialVersionUID = 950541833L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRpt rpt = new QRpt("rpt");

    public final QProduct prd;

    public final StringPath rptDes = createString("rptDes");

    public final StringPath rptId = createString("rptId");

    public final QUsr usr;

    public QRpt(String variable) {
        this(Rpt.class, forVariable(variable), INITS);
    }

    public QRpt(Path<? extends Rpt> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRpt(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRpt(PathMetadata metadata, PathInits inits) {
        this(Rpt.class, metadata, inits);
    }

    public QRpt(Class<? extends Rpt> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.prd = inits.isInitialized("prd") ? new QProduct(forProperty("prd"), inits.get("prd")) : null;
        this.usr = inits.isInitialized("usr") ? new QUsr(forProperty("usr")) : null;
    }

}

