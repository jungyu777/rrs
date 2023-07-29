package com.junjun.review.service;

import com.junjun.review.model.ReviewEntity;
import com.junjun.review.repository.RestaurantRepository;
import com.junjun.review.repository.ReviewRepository;
import com.junjun.review.service.dto.ReviewDto;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public void createReview(Long restaurantId, String content, Double score){

        restaurantRepository.findById(restaurantId).orElseThrow(); //없는 맛집이라면 에러를 냄

        ReviewEntity review =ReviewEntity.builder()
                .restaurantId(restaurantId)
                .content(content)
                .score(score)
                .createdAt(ZonedDateTime.now())
                .build();
        reviewRepository.save(review);
    }
    @Transactional
    public void deleteReview(Long reviewId){
        ReviewEntity review = reviewRepository.findById(reviewId).orElseThrow();

        reviewRepository.delete(review);
    }

    public ReviewDto getRestaurantReview(Long restaurantId, Pageable page){
        Double avgScore = reviewRepository.getAvgScoreByRestaurantId(restaurantId);
        Slice<ReviewEntity> reviews = reviewRepository.findSliceByRestaurantId(restaurantId,page);

        return ReviewDto.builder()
                .avgScore(avgScore)
                .reviews(reviews.getContent())
                .page(
                        ReviewDto.ReviewDtoPage.builder()
                                .offset(page.getPageNumber() * page.getPageSize())
                                .limit(page.getPageSize())
                                .build()
                )
                .build();
    }

}
