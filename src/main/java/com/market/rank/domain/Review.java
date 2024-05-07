package com.market.rank.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @EmbeddedId
    private Review.ReviewListsId reviewListsId;
    private String revDes;
    private int rateScr;
    @Embeddable
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewListsId implements Serializable {
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "prd_id")
        private Product prd;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "usr_id")
        private Usr usr;
    }
}
