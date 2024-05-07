package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsr is a Querydsl query type for Usr
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsr extends EntityPathBase<Usr> {

    private static final long serialVersionUID = 950544807L;

    public static final QUsr usr = new QUsr("usr");

    public final StringPath bdate = createString("bdate");

    public final StringPath detAddr = createString("detAddr");

    public final StringPath mail = createString("mail");

    public final StringPath phNum = createString("phNum");

    public final StringPath pstAddr = createString("pstAddr");

    public final StringPath rdAddr = createString("rdAddr");

    public final StringPath usrId = createString("usrId");

    public final StringPath usrNm = createString("usrNm");

    public QUsr(String variable) {
        super(Usr.class, forVariable(variable));
    }

    public QUsr(Path<? extends Usr> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsr(PathMetadata metadata) {
        super(Usr.class, metadata);
    }

}

