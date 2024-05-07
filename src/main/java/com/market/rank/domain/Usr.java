package com.market.rank.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usr {

    @Id
    private String usrId;

    private String phNum;

    private String usrNm;

    private String mail;

    private String bdate;

    private String pstAddr;

    private String rdAddr;

    private String detAddr;




}
