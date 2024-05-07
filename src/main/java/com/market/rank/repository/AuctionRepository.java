package com.market.rank.repository;

import com.market.rank.domain.Auction;
import com.market.rank.domain.Product;
import com.market.rank.domain.Usr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Auction.AuctionId> {
    Optional<Auction> findTopByAuctionIdPrdOrderByBidPriceDesc(Product product);

    void deleteByAuctionId(Auction.AuctionId auctionId);

    List<Auction> findByAuctionIdUsr(Usr usr);
}
