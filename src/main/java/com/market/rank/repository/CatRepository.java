package com.market.rank.repository;
import com.market.rank.domain.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat,String> {

    List<Cat> findByCatIdLike(String category);
}
