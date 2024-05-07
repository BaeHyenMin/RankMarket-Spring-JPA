package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWishLists is a Querydsl query type for WishLists
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWishLists extends EntityPathBase<WishLists> {

    private static final long serialVersionUID = -2133730047L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWishLists wishLists = new QWishLists("wishLists");

    public final QWishLists_WishListsId wishListsId;

    public QWishLists(String variable) {
        this(WishLists.class, forVariable(variable), INITS);
    }

    public QWishLists(Path<? extends WishLists> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWishLists(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWishLists(PathMetadata metadata, PathInits inits) {
        this(WishLists.class, metadata, inits);
    }

    public QWishLists(Class<? extends WishLists> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.wishListsId = inits.isInitialized("wishListsId") ? new QWishLists_WishListsId(forProperty("wishListsId"), inits.get("wishListsId")) : null;
    }

}

