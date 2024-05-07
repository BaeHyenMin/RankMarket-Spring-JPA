package com.market.rank.repository;

import com.market.rank.domain.Usr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsrRepository extends JpaRepository<Usr, String> {

    Optional<Usr> findByMail(String mail);

    boolean existsByPhNum(String phNum);
}
