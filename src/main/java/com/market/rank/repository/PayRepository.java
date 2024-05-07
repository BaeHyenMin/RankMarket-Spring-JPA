package com.market.rank.repository;

import com.market.rank.domain.Pay;
import com.market.rank.domain.Usr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayRepository extends JpaRepository<Pay, Pay.PayListsId> {
    List<Pay> findByPayListsIdUsr(Usr usr);

    Optional<Pay> findByPayListsId(Pay.PayListsId payListsId);
}
