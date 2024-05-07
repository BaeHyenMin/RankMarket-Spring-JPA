package com.market.rank.service.usr;

import com.market.rank.domain.Usr;
import com.market.rank.dto.request.ReqLoginUsrDto;
import com.market.rank.domain.*;
import com.market.rank.dto.NaverSmsDto;
import com.market.rank.dto.response.*;
import com.market.rank.repository.UsrRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsrViewService {

    private final UsrRepository usrRepository;

    private final JPAQueryFactory jpaQueryFactory;

    private final RedisTemplate<String, String> redisTemplate;


    public List<ResRptDto> reporthistory(String usr_id) {
        QRpt rpt = QRpt.rpt;
        QFiles files = QFiles.files;
        return jpaQueryFactory.select(Projections.fields(
                        ResRptDto.class,
                        files.flId.max().as("img"),
                        rpt.rptDes.as("rpt_des"),
                        rpt.rptId.as("rpt_id"),
                        rpt.rptId.substring(rpt.rptId.length().subtract(1)).as("rpt_type")
                )).from(rpt)
                .leftJoin(files).on(files.prd.eq(rpt.prd))
                .where(rpt.usr.usrId.eq(usr_id))
                .groupBy(rpt.rptDes, rpt.usr.usrId, rpt.rptId)
                .fetch();
    }


    public ResChatRoomDto chatroom(String requestUsrId) {
        QProduct product = QProduct.product;
        QChatRoom chatRoom = QChatRoom.chatRoom;
        QUsr usr = QUsr.usr;
        ResChatRoomDto resChatRoomDto = ResChatRoomDto.builder()
                .chatDto(jpaQueryFactory.select(Projections.fields(ResChatRoomDto.ResChatDto.class,

                                chatRoom.chatId.as("chat_id"),
                                product.title.as("prd_title")

                        )).from(chatRoom)
                        .leftJoin(product).on(chatRoom.prd.eq(product))
                        .leftJoin(usr).on(chatRoom.usr.eq(usr))
                        .where(chatRoom.usr.usrId.eq(requestUsrId).or(chatRoom.prd.usr.usrId.eq(requestUsrId)))
                        .fetch())
                .usr_id(requestUsrId)
                .build();

        resChatRoomDto.setUsr_id(requestUsrId);

        return resChatRoomDto;
    }


    public Optional<Usr> searchUsrInfo(String email) {
        return usrRepository.findByMail(email);
    }

    public String searchRefreshToken(ReqLoginUsrDto requestUsrDto) {
        return redisTemplate.opsForValue().get(requestUsrDto.getUsrId());
    }

    public boolean usrCheck(NaverSmsDto naverSmsDto) {
        return usrRepository.existsByPhNum(naverSmsDto.getPh_num());
    }


    public boolean codeCheck(NaverSmsDto naverSmsDto) {
        String code = redisTemplate.opsForValue().get(naverSmsDto.getPh_num());
        return code != null && code.equals(naverSmsDto.getPh_code());
    }

    public String usrPhNum(String usr_id) {
        return usrRepository.findById(usr_id).get().getPhNum();
    }


    public List<ResWishDto> usrWishLists(String usrId) {
        QProduct product = QProduct.product;
        QFiles files = QFiles.files;
        QAuction auction = QAuction.auction;
        QWishLists wishLists = QWishLists.wishLists;
        return jpaQueryFactory.select(Projections.fields(ResWishDto.class,
                        new CaseBuilder().when(product.bidCnt.eq(0))
                                .then(product.sellPrc)
                                .otherwise(auction.bidPrice.max())
                                .as("bid_price"),
                        product.ieastPrc.as("ieast_price"),
                        product.highPrc.as("high_price"),
                        product.title.as("title"),
                        files.flId.max().as("img"),
                        product.prdId.as("prd_id"),
                        Expressions.dateTemplate(String.class
                                , "TO_CHAR({0}, {1})", product.endDtm, ConstantImpl.create("YY/MM/DD HH24:MI:SS")).as("end_dtm")
                )).from(product)
                .leftJoin(files).on(files.prd.eq(product))
                .leftJoin(auction).on(auction.auctionId.prd.eq(product))
                .leftJoin(wishLists).on(wishLists.wishListsId.prd.eq(product))
                .where(wishLists.wishListsId.usr.usrId.eq(usrId))
                .groupBy(product.title, product.prdId, product.endDtm, product.sellPrc, product.bidCnt, product.highPrc, product.ieastPrc)
                .fetch();
    }

    public List<ResPrdmgmtDto> prdmgmt(String usrId) {
        QProduct product = QProduct.product;
        QFiles files = QFiles.files;
        QAuction auction = QAuction.auction;
        return jpaQueryFactory.select(Projections.fields(ResPrdmgmtDto.class,
                        product.title.as("title"),
                        product.prdId.as("prd_id"),
                        product.highPrc.as("high_price"),
                        product.ieastPrc.as("ieast_price"),
                        Expressions.dateTemplate(String.class
                                , "TO_CHAR({0}, {1})", product.endDtm, ConstantImpl.create("YY/MM/DD HH24:MI:SS")).as("end_dtm"),
                        product.status.as("status"),
                        product.sellPrc.as("sell_price"),
                        new CaseBuilder().when(product.bidCnt.eq(0)).then(product.sellPrc).otherwise(auction.bidPrice.max()).as("bid_price"),
                        product.bidCnt.as("bid_cnt")
                )).from(product)
                .leftJoin(auction).on(auction.auctionId.prd.prdId.eq(product.prdId))
                .where(product.usr.usrId.eq(usrId))
                .groupBy(product.title, product.prdId, product.highPrc, product.ieastPrc, product.endDtm, product.status, product.sellPrc, product.bidCnt)
                .fetch()
                .stream()
                .peek(resPrdmgmtDto -> {
                    List<String> images = jpaQueryFactory.select(files.flId)
                            .from(files)
                            .where(files.prd.prdId.eq(resPrdmgmtDto.getPrdId()))
                            .fetch();
                    resPrdmgmtDto.setImgs(images);
                }).collect(Collectors.toList());
    }


    public List<ResBidDto> bidhistory(String usrId) {
        QAuction auction = QAuction.auction;
        QProduct product = QProduct.product;
        QFiles files = QFiles.files;


        return jpaQueryFactory.select(
                        Projections.fields(ResBidDto.class,
                                files.flId.max().as("img"),
                                Expressions.dateTemplate(String.class
                                        , "TO_CHAR({0}, {1})", auction.bidDtm, ConstantImpl.create("YY/MM/DD HH24:MI:SS")).as("bid_dtm"),
                                auction.bidPrice.max().as("bid_price"),
                                product.prdId.as("prd_id"),
                                Expressions.dateTemplate(String.class
                                        , "TO_CHAR({0}, {1})", product.endDtm, ConstantImpl.create("YY/MM/DD HH24:MI:SS")).as("end_dtm"),
                                product.sellPrc.as("sell_price"),
                                product.highPrc.as("high_price"),
                                product.ieastPrc.as("ieast_price"),
                                product.title.as("title"),
                                product.status.as("status")))
                .from(auction)
                .leftJoin(auction.auctionId.prd, product)
                .leftJoin(files).on(auction.auctionId.prd.eq(files.prd))
                .where(auction.auctionId.usr.usrId.eq(usrId))
                .groupBy(auction.bidDtm, auction.bidPrice, product.prdId, product.endDtm, product.sellPrc, product.highPrc, product.ieastPrc
                        , product.title, product.status)
                .fetch();
    }


    public List<ResWinDto> winHistory(String usr_id) {
        QProduct product = QProduct.product;
        QFiles files = QFiles.files;
        QWin win = QWin.win;
        QPay pay = QPay.pay;
        QReview review = QReview.review;
        QRpt rpt = QRpt.rpt;

        return jpaQueryFactory
                .select(Projections.fields(ResWinDto.class,
                        files.flId.max().as("img"),
                        win.winPrice.as("win_price"),
                        new CaseBuilder()
                                .when(pay.payDtm.isNull()).then(1)
                                .otherwise(
                                        new CaseBuilder()
                                                .when(rpt.usr.usrId.isNull().and(review.reviewListsId.usr.usrId.isNull())).then(4)
                                                .otherwise(
                                                        new CaseBuilder()
                                                                .when(rpt.usr.usrId.isNotNull().and(review.reviewListsId.usr.usrId.isNull())).then(2)
                                                                .otherwise(3)
                                                )
                                ).as("status"),
                        product.prdId.as("prd_id"),
                        product.sellPrc.as("sell_price"),
                        product.title.as("title"),
                        product.highPrc.as("high_price")
                ))
                .from(win)
                .leftJoin(product).on(win.winListsId.prd.prdId.eq(product.prdId))
                .leftJoin(files).on(files.prd.prdId.eq(win.winListsId.prd.prdId))
                .leftJoin(review).on(review.reviewListsId.prd.prdId.eq(win.winListsId.prd.prdId))
                .leftJoin(rpt).on(rpt.prd.prdId.eq(win.winListsId.prd.prdId))
                .leftJoin(pay).on(pay.payListsId.prd.prdId.eq(win.winListsId.prd.prdId))
                .where(win.winListsId.usr.usrId.eq(usr_id))
                .groupBy(win.winPrice, product.prdId, product.sellPrc, product.title, product.highPrc, review.reviewListsId.usr.usrId, rpt.usr.usrId, pay.payDtm)
                .fetch();


    }


    public List<ResPayDto> payHistory(String usr_id, int status) {
        QFiles files = QFiles.files;
        QProduct product = QProduct.product;
        QWin win = QWin.win;
        QPay pay = QPay.pay;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(win.winListsId.usr.usrId.eq(usr_id));
        if (status == 1) {
            booleanBuilder.and(pay.payDtm.isNull());
        }
        return jpaQueryFactory.select(
                        Projections.fields(ResPayDto.class,
                                files.flId.max().as("img"),
                                product.title.as("title"),
                                win.winPrice.as("win_price"),
                                product.prdId.as("prd_id"),
                                Expressions.dateTemplate(String.class
                                        , "TO_CHAR({0}, {1})", pay.payDtm, ConstantImpl.create("YY/MM/DD HH24:MI:SS")).as("pay_dtm"),
                                new CaseBuilder().when(Expressions.dateTemplate(String.class
                                        , "TO_CHAR({0}, {1})", pay.payDtm, ConstantImpl.create("YY/MM/DD HH24:MI:SS")).isNull()).then(false).otherwise(true).as("pay_diff")
                        ))
                .from(product)
                .leftJoin(win).on(win.winListsId.prd.prdId.eq(product.prdId))
                .leftJoin(pay).on(pay.payListsId.prd.prdId.eq(product.prdId))
                .leftJoin(files).on(files.prd.prdId.eq(product.prdId))
                .where(booleanBuilder)
                .groupBy(win.winPrice, pay.payDtm, product.title, product.prdId)
                .fetch();

    }


    public List<ResChatMessagesDto> chatMessages(int pageNumber, String chat_id) {

        QChatMessages chatMessages = QChatMessages.chatMessages;
        return jpaQueryFactory.select(Projections.fields(ResChatMessagesDto.class,
                        chatMessages.chatMessageId.usr.usrId.as("usr_id"),
                        chatMessages.msg.as("msg"),
                        chatMessages.creDtm.as("cre_dtm"),
                        chatMessages.chatMessageId.chatRoom.chatId.as("chat_id")
                )).from(chatMessages)
                .where(chatMessages.chatMessageId.chatRoom.chatId.eq(chat_id))
                .orderBy(chatMessages.creDtm.desc())
                .offset(pageNumber)
                .limit(20)
                .fetch();

    }

    public List<ResAddrDto> chatAddress(String chat_id, String usr_id) {
        QUsr usr = QUsr.usr;
        QUsr subUsr = QUsr.usr;
        QProduct product = QProduct.product;
        QProduct subProduct = QProduct.product;
        QChatRoom chatRoom = QChatRoom.chatRoom;


        return jpaQueryFactory.select(Projections.fields(
                        ResAddrDto.class,
                        usr.rdAddr.as("buyer"),
                        ExpressionUtils.as(
                                JPAExpressions.select(subUsr.rdAddr)
                                        .from(subUsr)
                                        .where(subUsr.usrId.eq(subProduct.usr.usrId)), "seller"
                        ),
                        product.prdId.as("prd_id"),
                        new CaseBuilder()
                                .when(usr.usrId.eq(usr_id))
                                .then("")
                                .otherwise(usr.usrId)
                                .as("buyer_id")
                )).from(chatRoom)
                .leftJoin(product).on(product.eq(chatRoom.prd))
                .leftJoin(usr).on(usr.eq(chatRoom.usr))
                .where(chatRoom.chatId.eq(chat_id))
                .fetch();


    }

    public ResUsrInfoDto usrInfo(String usr_id) {

        QUsr usr = QUsr.usr;
        return jpaQueryFactory.select(Projections.fields(ResUsrInfoDto.class,
                        usr.rdAddr.as("rd_addr"),
                        usr.pstAddr.as("pst_addr"),
                        usr.rdAddr.as("rd_addr"),
                        usr.mail.as("mail"),
                        usr.phNum.as("ph_num"),
                        usr.detAddr.as("det_addr"),
                        usr.usrNm.as("name")
                ))
                .from(usr)
                .where(usr.usrId.eq(usr_id))
                .fetchFirst();
    }

    public List<ResReviewDto> myReviewHistory(String usr_id) {
        QReview review = QReview.review;
        QFiles files = QFiles.files;
        return jpaQueryFactory.select(Projections.fields(
                        ResReviewDto.class,
                        files.flId.max().as("img"),
                        review.reviewListsId.prd.prdId.as("prd_id"),
                        review.revDes.as("revDes"),
                        review.rateScr.as("rate_scr")
                )).from(review)
                .leftJoin(files).on(files.prd.eq(review.reviewListsId.prd))
                .where(review.reviewListsId.usr.usrId.eq(usr_id))
                .groupBy(review.revDes, review.reviewListsId.usr.usrId, review.rateScr, review.reviewListsId.prd.prdId)
                .fetch();
    }


    public List<ResReviewDto> myPrdReviewHistory(String usr_id) {
        QReview review = QReview.review;
        QFiles files = QFiles.files;
        QProduct subProduct = QProduct.product;

        return jpaQueryFactory.select(Projections.fields(
                        ResReviewDto.class,
                        files.flId.max().as("img"),
                        review.reviewListsId.prd.prdId.as("prd_id"),
                        review.revDes.as("revDes"),
                        review.rateScr.as("rate_scr")
                )).from(review)
                .leftJoin(files).on(files.prd.eq(review.reviewListsId.prd))
                .where(review.reviewListsId.prd.prdId.in(
                        JPAExpressions.select(subProduct.prdId)
                                .from(subProduct)
                                .where(subProduct.usr.usrId.eq(usr_id))
                ))
                .groupBy(review.revDes, review.reviewListsId.usr.usrId, review.rateScr, review.reviewListsId.prd.prdId)
                .fetch();
    }

}