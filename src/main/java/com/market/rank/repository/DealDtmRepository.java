package com.market.rank.repository;

import com.market.rank.domain.DealDtm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealDtmRepository extends JpaRepository<DealDtm, DealDtm.DealDtmId> {

}
