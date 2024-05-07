package com.market.rank.service.usr;


import com.market.rank.domain.*;
import com.market.rank.dto.request.ReqAddDelDtm;
import com.market.rank.dto.request.ReqLoginUsrDto;
import com.market.rank.repository.*;
import com.market.rank.service.api.FcmService;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UsrProcService {

    private final UsrRepository usrRepository;

    private final AuctionRepository auctionRepository;

    private final WishListsRepository wishListsRepository;

    private final WinRepository winRepository;

    private final PayRepository payRepository;

    private final ReviewRepository reviewRepository;

    private final JPAQueryFactory jpaQueryFactory;

    private final RedisTemplate<String, String> redisTemplate;

    private final RptRepository rptRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessagesRepository chatMessagesRepository;

    private final DealDtmRepository dealDtmRepository;

    private final DeviceRepository deviceRepository;

    private final FcmService fcmService;

    public Usr usrSave(com.market.rank.dto.request.ReqSignUpUsrDto reqSignUpUsrDto) {
        Usr usr = Usr.builder()
                .usrId("TEMP")
                .usrNm(reqSignUpUsrDto.getUsr_nm())
                .mail(reqSignUpUsrDto.getMail())
                .phNum(reqSignUpUsrDto.getPh_num())
                .bdate(reqSignUpUsrDto.getBdate())
                .pstAddr(reqSignUpUsrDto.getPst_addr())
                .rdAddr(reqSignUpUsrDto.getRd_addr())
                .detAddr(reqSignUpUsrDto.getDet_addr())
                .build();

        usrRepository.saveAndFlush(usr);
        usr = usrRepository.findByMail(usr.getMail()).get();
        if (reqSignUpUsrDto.getDevice_token() != null) {
            deviceTokenSave(reqSignUpUsrDto.getDevice_token(), usr);
        }
        return usr;

    }

    public void phoneSave(com.market.rank.dto.NaverSmsDto naverSmsDto) {
        redisTemplate.opsForValue().set(naverSmsDto.getPh_num(), naverSmsDto.getPh_code(), 3, TimeUnit.MINUTES);
    }

    public void refreshTokenSave(String refreshToken, ReqLoginUsrDto usr) {
        redisTemplate.opsForValue().set(usr.getUsrId(), refreshToken, 24, TimeUnit.HOURS);
    }


    @Transactional
    public void reviewSave(com.market.rank.dto.request.ReqAddReview review, String usr_id) {
        Review.ReviewListsId reviewListsId = Review.ReviewListsId.builder()
                .usr(Usr.builder().usrId(usr_id).build())
                .prd(Product.builder().prdId(review.getPrd_id()).build())
                .build();

        Review reviewEntitiy = Review.builder().reviewListsId(reviewListsId)
                .rateScr(review.getRateScr())
                .revDes(review.getRevDes())
                .build();

        reviewRepository.save(reviewEntitiy);
    }

    @Transactional
    public void bidend(int prd_id, String usr_id) {
        QProduct product = QProduct.product;
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        try {
            winRepository.winSave(prd_id);

            QDeviceToken deviceToken = QDeviceToken.deviceToken;

            QAuction auction = QAuction.auction;
            QAuction subAuction = QAuction.auction;
            Tuple tuple = jpaQueryFactory.select(deviceToken.token
                            , product.title)
                    .from(auction)
                    .leftJoin(deviceToken).on(deviceToken.deviceTokenId.usr.usrId.eq(auction.auctionId.usr.usrId))
                    .leftJoin(product).on(product.prdId.eq(auction.auctionId.prd.prdId))
                    .where(auction.bidPrice.eq(
                            jpaQueryFactory.select(auction.bidPrice.max())
                                    .from(subAuction)
                                    .where(subAuction.auctionId.prd.prdId.eq(prd_id)).fetchFirst()
                    ).and(auction.auctionId.prd.prdId.eq(prd_id)))
                    .fetchFirst();


            jpaQueryFactory.select(auction.bidPrice.max())
                    .from(subAuction)
                    .where(subAuction.auctionId.prd.prdId.eq(prd_id)).fetchFirst();

            String sellUsr = jpaQueryFactory.select(deviceToken.token)
                    .from(deviceToken)
                    .where(deviceToken.deviceTokenId.usr.usrId.eq(usr_id))
                    .fetchFirst();

            if (tuple.get(0,String.class) != null) {
                fcmService.send_FCM(tuple.get(0, String.class), "랭크마켓", tuple.get(1, String.class) + " 상품이 낙찰되었습니다.");
            }
            if (sellUsr != null) {
                fcmService.send_FCM(sellUsr, "랭크마켓", tuple.get(1, String.class) + " 상품이 낙찰되었습니다.");
            }
        } catch (Exception ignored) {
            System.out.println(ignored);
        }

        jpaQueryFactory.update(product)
                .set(product.endDtm, now.format(formatter))
                .where(product.prdId.eq(prd_id))
                .execute();

    }

    @Transactional
    public void rptSave(com.market.rank.dto.request.ReqAddRptDto report, String usr_id) {
        rptRepository.rptSave(usr_id, report.getPrdId(), report.getRptDes(), report.getRptId());
    }

    public void paySave(com.market.rank.dto.request.ReqPayDto pay, String usr_id) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddHHmmss");
        String nowDate = simpleDateFormat.format(date);

        List<Pay> pays = new ArrayList<>();

        List<ChatRoom> chatRooms = new ArrayList<>();

        for (int i = 0; i < pay.getPrdIds().size(); i++) {


            Pay.PayListsId payListsId = Pay.PayListsId.builder()
                    .usr(Usr.builder().usrId(usr_id).build())
                    .prd(Product.builder().prdId(pay.getPrdIds().get(i)).build())
                    .build();

            Pay payEntity = Pay.builder().payListsId(payListsId)
                    .payPrc(pay.getPayPrice().get(i))
                    .payDtm(nowDate)
                    .build();

            pays.add(payEntity);

            chatRooms.add(ChatRoom.builder()
                    .chatId("tmep" + i)
                    .usr(Usr.builder().usrId(usr_id).build())
                    .prd(Product.builder().prdId(pay.getPrdIds().get(i)).build())
                    .build());


        }

        payRepository.saveAll(pays);

        chatRoomRepository.saveAll(chatRooms);


    }

    @Transactional
    public void usrDelete(String usr_id) {
        usrRepository.deleteById(usr_id);
    }

    public void chatMassageSave(com.market.rank.dto.response.ResChatMessagesDto resChatMessagesDto) {
        ChatMessages.ChatMessageId chatMessageId = ChatMessages.ChatMessageId.builder()
                .usr(Usr.builder().usrId(resChatMessagesDto.getUsr_id()).build())
                .chatRoom(ChatRoom.builder().chatId(resChatMessagesDto.getChat_id()).build())
                .build();

        ChatMessages chatMessages = ChatMessages.builder()
                .chatMessageId(chatMessageId)
                .msg(resChatMessagesDto.getMsg())
                .build();

        chatMessagesRepository.save(chatMessages);


        QDeviceToken deviceToken = QDeviceToken.deviceToken;

        QUsr usr = QUsr.usr;
        QUsr subUsr = QUsr.usr;
        QProduct product = QProduct.product;
        QProduct subProduct = QProduct.product;
        QChatRoom chatRoom = QChatRoom.chatRoom;


        Tuple tuple = jpaQueryFactory.select(
                        usr.usrId,
                        JPAExpressions.select(subUsr.usrId)
                                .from(subUsr)
                                .where(subUsr.usrId.eq(product.usr.usrId))


                ).from(chatRoom)
                .leftJoin(product).on(product.eq(chatRoom.prd))
                .leftJoin(usr).on(usr.eq(chatRoom.usr))
                .where(chatRoom.chatId.eq(resChatMessagesDto.getChat_id()))
                .fetchFirst();

        String opponent = Objects.equals(tuple.get(0, String.class), resChatMessagesDto.getUsr_id()) ? tuple.get(1, String.class) : tuple.get(0, String.class);
        opponent = jpaQueryFactory.select(deviceToken.token)
                .from(deviceToken)
                .where(deviceToken.deviceTokenId.usr.usrId.eq(opponent))
                .fetchFirst();



        if (opponent != null) {
            fcmService.send_FCM(opponent, "채팅", chatMessages.getMsg());
        }


    }


    @Transactional
    public void usrUpdate(com.market.rank.dto.request.ReqUsrUpdateDto reqUsrUpdateDto, String usrId) {
        QUsr qUsr = QUsr.usr;
        for (int i = 0; i < reqUsrUpdateDto.getUpdateValue().size(); i++) {
            StringPath updateColumn = Expressions.stringPath(qUsr, reqUsrUpdateDto.getUpdateColumn().get(i));
            jpaQueryFactory
                    .update(qUsr)
                    .set(updateColumn, reqUsrUpdateDto.getUpdateValue().get(i))
                    .where(qUsr.usrId.eq(usrId))
                    .execute();
        }
    }

    public void changeToWishList(List<Integer> prdIds, String usr_id) {
        Usr usr = Usr.builder().usrId(usr_id).build();
        if (prdIds.size() > 1) {
            List<WishLists.WishListsId> wishListsId = new ArrayList<>();
            for (int prdId : prdIds) {
                Product prd = Product.builder().prdId(prdId).build();
                wishListsId.add(WishLists.WishListsId.builder().usr(usr).prd(prd).build());
            }
            wishListsRepository.deleteAllById(wishListsId);
        } else {
            Product prd = Product.builder().prdId(prdIds.get(0)).build();
            WishLists.WishListsId wishListsId = WishLists.WishListsId.builder().usr(usr).prd(prd).build();
            if (wishListsRepository.existsByWishListsId(wishListsId)) {
                wishListsRepository.deleteById(wishListsId);
            } else {
                wishListsRepository.save(WishLists.builder().wishListsId(wishListsId).build());
            }
        }

    }


    @Transactional
    public void reportDelete(List<Integer> rptIds) {
        for (int rptId : rptIds) {
            rptRepository.deleteById(String.valueOf(rptId));
        }

    }

    @Transactional
    public void reviewDelete(List<Integer> prdIds, String requestUsrId) {
        Usr usr = Usr.builder().usrId(requestUsrId).build();
        for (int prdId : prdIds) {
            Product prd = Product.builder().prdId(prdId).build();
            Review.ReviewListsId reviewListsId = Review.ReviewListsId.builder().usr(usr).prd(prd).build();
            reviewRepository.deleteById(reviewListsId);

        }
    }


    @Transactional
    public void bidDel(List<Integer> prdIds, String requestUsrId) {
        Usr usr = Usr.builder().usrId(requestUsrId).build();
        for (int prdId : prdIds) {
            Product prd = Product.builder().prdId(prdId).build();
            Auction.AuctionId auctionId = Auction.AuctionId.builder().usr(usr).prd(prd).build();
            auctionRepository.deleteByAuctionId(auctionId);
        }

    }


    public void delDtmSave(ReqAddDelDtm reqAddDelDtm) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd HH:mm");
        try {
            dealDtmRepository.save(DealDtm.builder()
                    .lat(reqAddDelDtm.getLat())
                    .lng(reqAddDelDtm.getLng())
                    .usr(Usr.builder().usrId(reqAddDelDtm.getUsrId()).build())
                    .dealDtmId(
                            DealDtm.DealDtmId
                                    .builder()
                                    .prd(Product.builder().prdId(reqAddDelDtm.getPrdId()).build())
                                    .build()
                    )
                    .dlTime(formatter.parse(reqAddDelDtm.getDelTime()))
                    .build()
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void deviceTokenSave(String token, Usr usr) {
        if(deviceRepository.existsById(DeviceToken.DeviceTokenId.builder().usr(usr).build())){
            deviceRepository.save(DeviceToken.builder()
                    .token(token)
                    .deviceTokenId(DeviceToken.DeviceTokenId.builder().usr(usr).build())
                    .build());

        }

    }
}
