package com.junjun.review.repository;

import com.junjun.review.model.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Long> {
}
