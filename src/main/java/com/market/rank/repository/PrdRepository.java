package com.market.rank.repository;

import com.market.rank.domain.Product;
import com.market.rank.domain.Usr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrdRepository extends JpaRepository<Product, Integer> {

    List<Product> findByUsr(Usr build);

    List<Product> findTop4ByOrderByBidCntDesc();

    List<Product> findTop4ByOrderByStartDtmDesc();



    List<Product> findTop3ByOrderByStartDtmDesc();

    void deleteByPrdIdAndUsrUsrId(int prdId, String usr_id);

    Product findByUsrUsrId(String usr_id);

}
