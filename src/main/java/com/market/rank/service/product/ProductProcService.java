package com.market.rank.service.product;

import com.market.rank.domain.*;
import com.market.rank.dto.request.ReqAddAuctionDto;
import com.market.rank.dto.request.ReqAddPrdDto;
import com.market.rank.dto.request.ReqUpdatePrdDto;
import com.market.rank.repository.AuctionRepository;
import com.market.rank.repository.FilesRepository;
import com.market.rank.repository.PrdRepository;
import com.market.rank.repository.WinRepository;
import com.market.rank.service.api.AwsService;
import com.market.rank.service.api.FcmService;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductProcService {

    private final PrdRepository prdRepository;
    private final FilesRepository filesRepository;
    private final AuctionRepository auctionRepository;
    private final WinRepository winRepository;
    private final AwsService awsService;

    private final JPAQueryFactory jpaQueryFactory;
    private final FcmService fcmService;

    public void productAdd(ReqAddPrdDto reqAddPrdDto) {

        Product product = prdRepository.save(Product.builder()
                .usr(Usr.builder().usrId(reqAddPrdDto.getUsrId()).build())
                .des(reqAddPrdDto.getDes())
                .sellPrc(reqAddPrdDto.getSellPrice())
                .title(reqAddPrdDto.getTitle())
                .highPrc(reqAddPrdDto.getHighPrice())
                .ieastPrc(reqAddPrdDto.getIseatPrice())
                .significant(reqAddPrdDto.getSignificant())
                .catId(Cat.builder().catId(reqAddPrdDto.getCatId()).build())
                .status("1")
                .build());


        for (MultipartFile multipart : reqAddPrdDto.getImages()) {
            String fileId = awsService.uploadToAWS(multipart);
            filesRepository.save(Files.builder()
                    .flId(fileId)
                    .prd(product)
                    .build());
        }


    }

    @Transactional
    public void auctionAdd(ReqAddAuctionDto reqAddAuctionDto) {
        QProduct qProduct = QProduct.product;
        QAuction qAuction = QAuction.auction;
        int prices = Optional.ofNullable(jpaQueryFactory.select(new CaseBuilder()
                        .when(qProduct.bidCnt.eq(0))
                        .then(qProduct.sellPrc)
                        .otherwise(qAuction.bidPrice.max()).as("sell_price"))
                .from(qProduct)
                .leftJoin(qAuction).on(qAuction.auctionId.prd.eq(qProduct))
                .where(qProduct.prdId.eq(reqAddAuctionDto.getPrdId()))
                .groupBy(qProduct.bidCnt, qProduct.sellPrc)
                .fetchOne()).orElse(0);


        if (!(reqAddAuctionDto.getHighPrice() < (prices + reqAddAuctionDto.getIeastPrice()))) {
            auctionRepository.save(Auction.builder().auctionId(
                    Auction.
                            AuctionId.builder().
                            prd(Product.builder().
                                    prdId(reqAddAuctionDto.getPrdId()).build()).
                            usr(Usr.builder().
                                    usrId(reqAddAuctionDto.getUsrId()).build()).
                            build()
            ).bidPrice(reqAddAuctionDto.getIeastPrice() + prices).build());


            Integer currentBidCnt = Optional.ofNullable(
                            jpaQueryFactory.select(qProduct.bidCnt)
                                    .from(qProduct)
                                    .where(qProduct.prdId.eq(reqAddAuctionDto.getPrdId()))
                                    .fetchOne())
                    .orElse(0);


            jpaQueryFactory.update(qProduct)
                    .set(qProduct.bidCnt, currentBidCnt + 1)
                    .where(qProduct.prdId.eq(reqAddAuctionDto.getPrdId()))
                    .execute();

            if ((reqAddAuctionDto.getHighPrice() - (prices + (reqAddAuctionDto.getIeastPrice() * 2))) < 0) {
                LocalDate now = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
                winRepository.winSave(reqAddAuctionDto.getPrdId());
                jpaQueryFactory.update(qProduct)
                        .set(qProduct.endDtm, now.format(formatter))
                        .where(qProduct.prdId.eq(reqAddAuctionDto.getPrdId()))
                        .execute();

                QProduct product = QProduct.product;
                QDeviceToken deviceToken = QDeviceToken.deviceToken;

                Tuple tuple = jpaQueryFactory.select(deviceToken.token,
                                product.title)
                        .from(product)
                        .leftJoin(deviceToken).on(deviceToken.deviceTokenId.usr.usrId.eq(product.usr.usrId))
                        .where(product.prdId.eq(reqAddAuctionDto.getPrdId()))
                        .fetchFirst();

                String myDeviceToken = jpaQueryFactory.select(deviceToken.token)
                        .from(deviceToken)
                        .where(deviceToken.deviceTokenId.usr.usrId.eq(reqAddAuctionDto.getUsrId()))
                        .fetchFirst();


                if (tuple.get(0, String.class) != null) {
                    fcmService.send_FCM(tuple.get(0, String.class), "랭크마켓", tuple.get(1, String.class) + " 상품이 낙찰되었습니다.");
                }
                if (myDeviceToken != null) {
                    fcmService.send_FCM(myDeviceToken, "랭크마켓", tuple.get(1, String.class) + " 상품이 낙찰되었습니다.");
                }


            }


        }


    }

    @Transactional
    public void prdUpdate(ReqUpdatePrdDto updatePrdDto) {
        QProduct qProduct = QProduct.product;

        jpaQueryFactory.update(qProduct)
                .set(qProduct.title, updatePrdDto.getTitle())
                .set(qProduct.significant, updatePrdDto.getSignificant())
                .set(qProduct.ieastPrc, updatePrdDto.getIeastPrice())
                .set(qProduct.des, updatePrdDto.getDes())
                .where(qProduct.usr.usrId.eq(updatePrdDto.getUsrId()).and(qProduct.prdId.eq(updatePrdDto.getPrdId())))
                .execute();


    }

    @Transactional
    public void prdDelete(int prdId, String usr_id) {
        List<String> files = Collections.singletonList(filesRepository.findByPrdPrdId(prdId).stream()
                .map(Files::getFlId).toString());
        for (String file : files) {
            awsService.deleteToAWS(file);
        }

        prdRepository.deleteByPrdIdAndUsrUsrId(prdId, usr_id);
    }

    @Transactional
    public boolean winSave() {
        QWin qWin = QWin.win;
        QAuction qAuction = QAuction.auction;
        try {
            jpaQueryFactory.insert(qWin)
                    .columns(qWin.winListsId.prd.prdId, qWin.winListsId.usr.usrId, qWin.winPrice)
                    .select(
                            new JPAQuery<>()
                                    .select(qAuction.auctionId.prd, qAuction.auctionId.usr.usrId, qAuction.bidPrice.max())
                                    .from(qAuction)
                                    .where(qAuction.auctionId.prd.prdId.eq(17))
                                    .groupBy(qAuction.auctionId.prd, qAuction.auctionId.usr.usrId)
                    )
                    .execute();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
