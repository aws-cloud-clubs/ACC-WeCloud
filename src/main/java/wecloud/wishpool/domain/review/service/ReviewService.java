package wecloud.wishpool.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wecloud.wishpool.domain.review.dto.request.ReviewCreateRequestDto;
import wecloud.wishpool.domain.review.dto.response.ReviewGetResponseDto;
import wecloud.wishpool.domain.review.entity.Review;
import wecloud.wishpool.domain.review.repository.ReviewRepository;
import wecloud.wishpool.domain.wish.entity.Wish;
import wecloud.wishpool.domain.wish.service.WishService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final WishService wishService;

    @Transactional
    public Long createReview(Long wishId, ReviewCreateRequestDto requestDto) {
        Wish wish = wishService.findByWishId(wishId);
        return reviewRepository.save(toEntity(wish, requestDto)).getId();
    }

    private Review toEntity(Wish wish, ReviewCreateRequestDto requestDto) {
        return Review.builder()
                .content(requestDto.getContent())
                .image(requestDto.getImage())
                .wish(wish)
                .build();
    }

    public ReviewGetResponseDto findReview(Long reviewId, Long wishId) {
        Review review = findByReviewId(reviewId);
        if (review.getWish().getId() != wishId) {
            throw new IllegalArgumentException("해당 리뷰를 조회할 수 없습니다.");
        }
        return ReviewGetResponseDto.builder()
                .id(review.getId())
                .content(review.getContent())
                .image(review.getImage())
                .build();
    }

    public Review findByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));
    }
}
