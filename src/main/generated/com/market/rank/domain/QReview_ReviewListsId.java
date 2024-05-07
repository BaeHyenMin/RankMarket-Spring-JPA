package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview_ReviewListsId is a Querydsl query type for ReviewListsId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QReview_ReviewListsId extends BeanPath<Review.ReviewListsId> {

    private static final long serialVersionUID = -919562961L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview_ReviewListsId reviewListsId = new QReview_ReviewListsId("reviewListsId");

    public final QProduct prd;

    public final QUsr usr;

    public QReview_ReviewListsId(String variable) {
        this(Review.ReviewListsId.class, forVariable(variable), INITS);
    }

    public QReview_ReviewListsId(Path<? extends Review.ReviewListsId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview_ReviewListsId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview_ReviewListsId(PathMetadata metadata, PathInits inits) {
        this(Review.ReviewListsId.class, metadata, inits);
    }

    public QReview_ReviewListsId(Class<? extends Review.ReviewListsId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.prd = inits.isInitialized("prd") ? new QProduct(forProperty("prd"), inits.get("prd")) : null;
        this.usr = inits.isInitialized("usr") ? new QUsr(forProperty("usr")) : null;
    }

}

