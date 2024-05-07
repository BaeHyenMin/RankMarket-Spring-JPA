package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWishLists_WishListsId is a Querydsl query type for WishListsId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QWishLists_WishListsId extends BeanPath<WishLists.WishListsId> {

    private static final long serialVersionUID = 1149160284L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWishLists_WishListsId wishListsId = new QWishLists_WishListsId("wishListsId");

    public final QProduct prd;

    public final QUsr usr;

    public QWishLists_WishListsId(String variable) {
        this(WishLists.WishListsId.class, forVariable(variable), INITS);
    }

    public QWishLists_WishListsId(Path<? extends WishLists.WishListsId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWishLists_WishListsId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWishLists_WishListsId(PathMetadata metadata, PathInits inits) {
        this(WishLists.WishListsId.class, metadata, inits);
    }

    public QWishLists_WishListsId(Class<? extends WishLists.WishListsId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.prd = inits.isInitialized("prd") ? new QProduct(forProperty("prd"), inits.get("prd")) : null;
        this.usr = inits.isInitialized("usr") ? new QUsr(forProperty("usr")) : null;
    }

}

