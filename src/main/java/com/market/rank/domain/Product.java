package com.market.rank.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prd_seq")
    @SequenceGenerator(name = "prd_seq", sequenceName = "prd_seq", initialValue = 1, allocationSize = 1)
    private int prdId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    private Cat catId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usr_id")
    private Usr usr;
    private String title;
    private int sellPrc;
    private int highPrc;
    private int ieastPrc;
    private String des;
    private int bidCnt;
    private String significant;
    @Builder.Default
    private String endDtm = getCurrentDateAfter7Days();
    @UpdateTimestamp
    private Date startDtm;
    private String status;


    private static String getCurrentDateAfter7Days() {
        LocalDate now = LocalDate.now().plusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");
        return now.format(formatter);
    }
}
