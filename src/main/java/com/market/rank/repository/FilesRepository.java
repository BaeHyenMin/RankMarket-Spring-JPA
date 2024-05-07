package com.market.rank.repository;

import com.market.rank.domain.Files;
import com.market.rank.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilesRepository extends JpaRepository<Files, String > {

    Optional<Files> findFirstByPrd(Product product);

    List<Files> findByPrd(Product product);

    List<Files> findTop4ByPrd(Product product);

    void deleteByPrdPrdId(int prd_id);

    List<Files> findByPrdPrdId(int prdId);
}
