package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWin is a Querydsl query type for Win
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWin extends EntityPathBase<Win> {

    private static final long serialVersionUID = 950546415L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWin win = new QWin("win");

    public final DateTimePath<java.util.Date> winDtm = createDateTime("winDtm", java.util.Date.class);

    public final QWin_WinListsId winListsId;

    public final NumberPath<Integer> winPrice = createNumber("winPrice", Integer.class);

    public QWin(String variable) {
        this(Win.class, forVariable(variable), INITS);
    }

    public QWin(Path<? extends Win> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWin(PathMetadata metadata, PathInits inits) {
        this(Win.class, metadata, inits);
    }

    public QWin(Class<? extends Win> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.winListsId = inits.isInitialized("winListsId") ? new QWin_WinListsId(forProperty("winListsId"), inits.get("winListsId")) : null;
    }

}

