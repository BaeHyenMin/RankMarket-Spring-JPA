package com.market.rank.repository;

import com.market.rank.domain.Usr;
import com.market.rank.domain.WishLists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListsRepository extends JpaRepository<WishLists, WishLists.WishListsId> {
    List<WishLists> findByWishListsIdUsr(Usr usr);
    boolean existsByWishListsId(WishLists.WishListsId wishListsId);
}
