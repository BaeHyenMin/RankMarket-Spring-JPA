package com.market.rank.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auction {
    @EmbeddedId
    Auction.AuctionId auctionId;
    private int bidPrice;
    @UpdateTimestamp
    private Date bidDtm;

    @Embeddable
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuctionId implements Serializable {
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "prd_id")
        private Product prd;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "usr_id")
        private Usr usr;
    }
}
