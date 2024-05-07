package com.market.rank.repository;

import com.market.rank.domain.Rpt;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RptRepository extends JpaRepository<Rpt,String> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "insert into rpt " +
            " values (to_char(sysdate, 'YYMMDD')||''||to_char(lpad(rpt_seq.nextval,2,'0')) ||''||:rpt_id, :usr_id, :prd_id , :rpt_des)", nativeQuery = true)
    void rptSave(@Param("usr_id") String usr_id, @Param("prd_id") int prd_id, @Param("rpt_des") String rpt_des, @Param("rpt_id") String rpt_id);



}
