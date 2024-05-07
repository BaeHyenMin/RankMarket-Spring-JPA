package com.market.rank.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.domain.Persistable;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Win implements Persistable<Win.WinListsId> {
    @EmbeddedId
    Win.WinListsId winListsId;
    private int winPrice;
    @UpdateTimestamp
    private Date winDtm;

    @Nullable
    @Override
    public WinListsId getId() {
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
    public static class WinListsId implements Serializable {
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "prd_id")
        private Product prd;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "usr_id")
        private Usr usr;
    }
}
