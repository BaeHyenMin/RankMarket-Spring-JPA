package com.market.rank.service.product;

import com.market.rank.domain.QAuction;
import com.market.rank.domain.QFiles;
import com.market.rank.domain.QProduct;
import com.market.rank.domain.QWishLists;
import com.market.rank.dto.response.ResPrdDto;
import com.market.rank.dto.response.ResPrdMostDto;
import com.market.rank.dto.response.ResPrdPopDto;
import com.market.rank.dto.response.ResPrdsDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductViewService {

    private final JPAQueryFactory jpaQueryFactory;



    @Transactional(readOnly = true)
    public List<ResPrdsDto> products(int endPoint, String usr_id, String search) {
        QProduct product = QProduct.product;
        QFiles files = QFiles.files;
        QWishLists wishLists = QWishLists.wishLists;
        QAuction auction = QAuction.auction;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (search != null) {
            booleanBuilder.and(product.title.contains(search));
        }


        return jpaQueryFactory.select(Projections.fields(ResPrdsDto.class,
                        files.flId.max().as("img"),
                        product.title.as("title"),
                        product.prdId.as("prd_id"),
                        new CaseBuilder()
                                .when(auction.bidPrice.max().isNull())
                                .then(product.sellPrc)
                                .otherwise(auction.bidPrice.max())
                                .as("sell_price"),
                        Expressions.dateTemplate(String.class
                                , "TO_CHAR({0}, {1})", product.endDtm, ConstantImpl.create("YY/MM/DD HH24:MI:SS")).as("end_dtm"),
                        new CaseBuilder()
                                .when(JPAExpressions.select(wishLists.wishListsId.usr.usrId)
                                        .from(wishLists)
                                        .where(wishLists.wishListsId.usr.usrId.eq(usr_id)
                                                .and(wishLists.wishListsId.prd.prdId.eq(product.prdId))).isNull()).then(false)
                                .otherwise(true)
                                .as("wish"),
                        product.highPrc.as("high_price")


                )).from(product)
                .leftJoin(files).on(files.prd.prdId.eq(product.prdId))
                .leftJoin(auction).on(auction.auctionId.prd.prdId.eq(product.prdId))
                .where(booleanBuilder)
                .groupBy(product.title, product.prdId, product.sellPrc, product.endDtm, product.highPrc, product.bidCnt, product.sellPrc)
                .orderBy(product.endDtm.desc())
                .fetchJoin()
                .offset(endPoint)
                .limit(20)
                .fetch();


    }


    public ResPrdDto product(int prd_id) {
        QProduct product = QProduct.product;
        QAuction auction = QAuction.auction;
        QFiles files = QFiles.files;

        ResPrdDto resPrdDto = jpaQueryFactory.select(Projections.fields(ResPrdDto.class,
                        product.title.as("title"),
                        new CaseBuilder()
                                .when(auction.bidPrice.max().isNull())
                                .then(product.sellPrc)
                                .otherwise(auction.bidPrice.max())
                                .as("bid_price"),
                        product.sellPrc.as("sell_price"),
                        product.highPrc.as("high_price"),
                        product.ieastPrc.as("ieast_price"),
                        product.significant.as("significant"),
                        product.prdId.as("prd_id"),
                        product.des.as("des"), Expressions.dateTemplate(String.class
                                , "TO_CHAR({0}, {1})", product.endDtm, ConstantImpl.create("YY/MM/DD HH24:MI:SS")).as("end_dtm")
                )).from(product)
                .leftJoin(auction).on(auction.auctionId.prd.eq(product))
                .where(product.prdId.eq(prd_id))
                .groupBy(product.title, product.des, product.highPrc, product.sellPrc, product.ieastPrc, product.endDtm, product.bidCnt, product.prdId,product.significant)
                .fetchFirst();

        List<String> fileIds = jpaQueryFactory.select(files.flId)
                .from(files)
                .where(files.prd.prdId.eq(prd_id))
                .fetch();


        resPrdDto.setImgs(fileIds);

        return resPrdDto;

    }


    public List<ResPrdPopDto> prdPopular() {
        Date current = new Date();
        QProduct product = QProduct.product;
        QFiles files = QFiles.files;
        return jpaQueryFactory.select(Projections.fields(ResPrdPopDto.class,
                        product.prdId.as("prd_id"),
                        product.title.as("title"),
                        product.ieastPrc.as("ieast_price"),
                        product.highPrc.as("high_price"),
                        files.flId.max().as("img")
                )).from(product)
                .leftJoin(files).on(files.prd.eq(product))
                .fetchJoin()
                .where(Expressions.stringTemplate("TO_CHAR({0}, {1})", product.endDtm, "YYYY/MM/DD HH24:MI:SS")
                        .goe(Expressions.stringTemplate("TO_CHAR({0}, {1})", current, "YYYY/MM/DD HH24:MI:SS")))
                .groupBy(product.title, product.prdId, product.ieastPrc, product.bidCnt, product.highPrc, product.endDtm)
                .offset(0)
                .limit(4)
                .orderBy(product.bidCnt.desc())
                .fetch();


    }


    public List<ResPrdMostDto> prdMost() {
        QProduct product = QProduct.product;
        QFiles files = QFiles.files;
        return jpaQueryFactory.select(Projections.fields(ResPrdMostDto.class,
                        product.prdId.max().as("prd_id"),
                        product.title.as("title"),
                        product.highPrc.as("high_price"),
                        product.ieastPrc.as("ieast_price")
                )).from(product)
                .leftJoin(files).on(files.prd.eq(product))
                .groupBy(product.title, product.ieastPrc, product.endDtm, product.highPrc)
                .orderBy(product.endDtm.desc())
                .fetchJoin()
                .offset(0)
                .limit(3)
                .fetch()
                .stream()
                .peek(ResPrdMostDto -> {
                    List<String> fileIds = jpaQueryFactory.select(files.flId)
                            .from(files)
                            .where(files.prd.prdId.eq(ResPrdMostDto.getPrdId()))
                            .fetch();
                    ResPrdMostDto.setImg(fileIds);

                }).collect(Collectors.toList());


    }



}
