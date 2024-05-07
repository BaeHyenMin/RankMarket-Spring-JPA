package com.market.rank.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Files {
    @Id
    private String flId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="prd_id")
    private Product prd;
}
