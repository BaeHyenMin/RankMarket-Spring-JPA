package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -445780094L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final NumberPath<Integer> bidCnt = createNumber("bidCnt", Integer.class);

    public final QCat catId;

    public final StringPath des = createString("des");

    public final StringPath endDtm = createString("endDtm");

    public final NumberPath<Integer> highPrc = createNumber("highPrc", Integer.class);

    public final NumberPath<Integer> ieastPrc = createNumber("ieastPrc", Integer.class);

    public final NumberPath<Integer> prdId = createNumber("prdId", Integer.class);

    public final NumberPath<Integer> sellPrc = createNumber("sellPrc", Integer.class);

    public final StringPath significant = createString("significant");

    public final DateTimePath<java.util.Date> startDtm = createDateTime("startDtm", java.util.Date.class);

    public final StringPath status = createString("status");

    public final StringPath title = createString("title");

    public final QUsr usr;

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.catId = inits.isInitialized("catId") ? new QCat(forProperty("catId")) : null;
        this.usr = inits.isInitialized("usr") ? new QUsr(forProperty("usr")) : null;
    }

}

