package com.market.rank.repository;

import com.market.rank.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Review.ReviewListsId> {
}
