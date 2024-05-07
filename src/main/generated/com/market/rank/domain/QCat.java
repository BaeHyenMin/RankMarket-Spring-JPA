package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCat is a Querydsl query type for Cat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCat extends EntityPathBase<Cat> {

    private static final long serialVersionUID = 950526953L;

    public static final QCat cat = new QCat("cat");

    public final StringPath catId = createString("catId");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public QCat(String variable) {
        super(Cat.class, forVariable(variable));
    }

    public QCat(Path<? extends Cat> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCat(PathMetadata metadata) {
        super(Cat.class, metadata);
    }

}

