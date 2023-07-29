package com.junjun.review.service.dto;

import com.junjun.review.model.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
@Builder
@AllArgsConstructor
public class ReviewDto {
    private Double avgScore;
    private List<ReviewEntity>  reviews;
    private ReviewDtoPage page;
    @Getter
    @AllArgsConstructor
    @Builder
    public static class ReviewDtoPage{
        private Integer offset;
        private Integer limit;
    }

}
