package com.junjun.review.repository;

import com.junjun.review.model.QReviewEntity;
import com.junjun.review.model.ReviewEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    //어떤 맛집에 등록된 평균 별점을 가져오는 쿼리문
    @Override
    public Double getAvgScoreByRestaurantId(Long restaurantId) {
        return queryFactory.select(QReviewEntity.reviewEntity.score.avg()) //reviewEntity 에 점수들을 평균을 낸다
                .from(QReviewEntity.reviewEntity)
                .where(QReviewEntity.reviewEntity.restaurantId.eq(restaurantId))// reviewEntity 중에 우리가 알고 싶은 맛집에 등록된 리뷰들의 점수의 의미 WHERE
                .fetchFirst();
    }

    /**
     *
     * 어떤 맛집에 등록된 리뷰들을 List 타입의 reviews 로 가져왔다
     * offset 과 limit 을 추가해 주었는데
     * 0번부터 9번까지 가져왔을떄 다음꼐 있을까 없을 까 정도는 클라이언트에게 알려줘야한다
     * 왜냐하면 클라이언트가 다음껄 요청을 해야할지 말아야할지  결정할수 있어야 하기 떄문이다
     * 그래서 limit 해서 + 1을 해준다
     * 예를들어서 클라이언트에서  10개를 가져와줘 라고 했는데  우리는 실제로 11개를 가져온다
     * 그 이유는  reviews.size() > page.getPageSize() 에 이유가 있다
     * 만약 10개를 가져오라고 했는데 11개를 가져왔다면  실제로는 10개를 가져온것보다 1개가 더 많다는 이야기이다
     * 너는 그럼 다음페이지도 요청할수 있다는 의미가 된다
     *
     */
    @Override
    public Slice<ReviewEntity> findSliceByRestaurantId(Long restaurantId, Pageable page) {
        List<ReviewEntity> reviews =queryFactory.select(QReviewEntity.reviewEntity)//reviews 라고하는 LIst 를 받아보는 쿼리 reviewEntity 를 리턴할것이기 떄문에  QReviewEntity.reviewEntity 까지만 셀렉트한다
                .from(QReviewEntity.reviewEntity)
                .where(QReviewEntity.reviewEntity.restaurantId.eq(restaurantId))
                .offset((long) page.getPageNumber() * page.getPageSize()) //offset 0개부터 10개를 가져와라 라고하면 0번부터 9번까지 가져온다
                .limit(page.getPageSize() + 1)
                .fetch();

        return new SliceImpl<>(
                reviews.stream().limit(page.getPageSize()).toList(),
                page,
                reviews.size() > page.getPageSize()
                );
    }
}
