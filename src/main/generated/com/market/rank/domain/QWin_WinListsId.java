package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWin_WinListsId is a Querydsl query type for WinListsId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QWin_WinListsId extends BeanPath<Win.WinListsId> {

    private static final long serialVersionUID = -1048598637L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWin_WinListsId winListsId = new QWin_WinListsId("winListsId");

    public final QProduct prd;

    public final QUsr usr;

    public QWin_WinListsId(String variable) {
        this(Win.WinListsId.class, forVariable(variable), INITS);
    }

    public QWin_WinListsId(Path<? extends Win.WinListsId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWin_WinListsId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWin_WinListsId(PathMetadata metadata, PathInits inits) {
        this(Win.WinListsId.class, metadata, inits);
    }

    public QWin_WinListsId(Class<? extends Win.WinListsId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.prd = inits.isInitialized("prd") ? new QProduct(forProperty("prd"), inits.get("prd")) : null;
        this.usr = inits.isInitialized("usr") ? new QUsr(forProperty("usr")) : null;
    }

}

