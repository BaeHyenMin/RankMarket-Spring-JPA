package com.market.rank.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rpt {
    @Id
    private String rptId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prd_id")
    private Product prd;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id")
    private Usr usr;
    private String rptDes;
}
