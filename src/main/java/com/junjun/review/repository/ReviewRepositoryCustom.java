package com.junjun.review.repository;

import com.junjun.review.model.ReviewEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ReviewRepositoryCustom {

    Double getAvgScoreByRestaurantId(Long restaurantId);
    Slice<ReviewEntity> findSliceByRestaurantId(Long restaurantId, Pageable page);
    /**
     * Slice 페이징 기법을 사용하는 인터페이스 중 하나인데  이것을 사용하기 위해서는 Pageable 이라는 파라미터를 함꼐 받아야 한다
     */

}
