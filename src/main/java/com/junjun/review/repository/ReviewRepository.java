package com.junjun.review.repository;

import com.junjun.review.model.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>,ReviewRepositoryCustom{
}
