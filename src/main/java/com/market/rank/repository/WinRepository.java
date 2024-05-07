package com.market.rank.repository;

import com.market.rank.domain.Usr;
import com.market.rank.domain.Win;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WinRepository extends JpaRepository<Win, Win.WinListsId> {
    List<Win> findByWinListsIdUsr(Usr usr);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into win (usr_id, prd_id, win_price, win_dtm) " +
            " select a.usr_id, a.prd_id, a.bid_price, sysdate " +
            " from auction a " +
            " where a.bid_price= (select max(subA.bid_price) " +
            " from auction subA " +
            " where subA.prd_id=?1) and a.prd_id = ?1 ", nativeQuery = true)
    void winSave(int prd_id);


    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into win w (w.usr_id, w.prd_id, w.win_price, w.win_dtm) " +
            " select a.usr_id, a.prd_id, a.bid_price, sysdate " +
            " from auction a " +
            " inner join product p on a.prd_id = p.prd_id " +
            " where a.bid_price= (select max(subA.bid_price) " +
            " from auction subA " +
            " where subA.prd_id=p.prd_id) and a.prd_id = p.prd_id " +
            " and TO_CHAR(p.end_dtm, 'YYYY/MM/DD HH24:MI:SS') = TO_CHAR(TRUNC(SYSDATE), 'YYYY/MM/DD') || ' 00:00:00'"
            , nativeQuery = true)
    void winSave();
}