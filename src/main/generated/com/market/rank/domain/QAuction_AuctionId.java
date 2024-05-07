package com.market.rank.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAuction_AuctionId is a Querydsl query type for AuctionId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QAuction_AuctionId extends BeanPath<Auction.AuctionId> {

    private static final long serialVersionUID = -1462942842L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAuction_AuctionId auctionId = new QAuction_AuctionId("auctionId");

    public final QProduct prd;

    public final QUsr usr;

    public QAuction_AuctionId(String variable) {
        this(Auction.AuctionId.class, forVariable(variable), INITS);
    }

    public QAuction_AuctionId(Path<? extends Auction.AuctionId> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAuction_AuctionId(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAuction_AuctionId(PathMetadata metadata, PathInits inits) {
        this(Auction.AuctionId.class, metadata, inits);
    }

    public QAuction_AuctionId(Class<? extends Auction.AuctionId> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.prd = inits.isInitialized("prd") ? new QProduct(forProperty("prd"), inits.get("prd")) : null;
        this.usr = inits.isInitialized("usr") ? new QUsr(forProperty("usr")) : null;
    }

}

