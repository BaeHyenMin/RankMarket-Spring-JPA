package com.market.rank.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pay  implements Persistable<Pay.PayListsId> {
    @EmbeddedId
    private Pay.PayListsId payListsId;
    private int payPrc;
    private String payDtm;

    @Override
    public PayListsId getId() {
        return null;
    }

    @Override
    public boolean isNew() {
        return true;
    }


    @Embeddable
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PayListsId implements Serializable {
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "prd_id")
        private Product prd;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "usr_id")
        private Usr usr;
    }
}
