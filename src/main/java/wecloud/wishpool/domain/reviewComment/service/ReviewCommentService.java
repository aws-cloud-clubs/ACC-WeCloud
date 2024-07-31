package wecloud.wishpool.domain.reviewComment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wecloud.wishpool.domain.review.entity.Review;
import wecloud.wishpool.domain.review.service.ReviewService;
import wecloud.wishpool.domain.reviewComment.dto.request.ReviewCommentCreateRequestDto;
import wecloud.wishpool.domain.reviewComment.dto.response.ReviewCommentGetResponseDto;
import wecloud.wishpool.domain.reviewComment.entity.ReviewComment;
import wecloud.wishpool.domain.reviewComment.repository.ReviewCommentRepository;
import wecloud.wishpool.domain.user.entity.User;
import wecloud.wishpool.domain.user.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewCommentService {
    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewService reviewService;
    private final UserService userService;

    @Transactional
    public Long createReviewComment(Long wishId, Long reviewId, Long userId, ReviewCommentCreateRequestDto requestDto) {
        Review review = reviewService.findByReviewId(reviewId);
        User user = userService.findByUserId(userId);
        if (review.getWish().getId() != wishId) {
            throw new IllegalArgumentException("해당 리뷰를 조회할 수 없습니다.");
        }
        return reviewCommentRepository.save(toEntity(user, review, requestDto)).getId();

    }

    private ReviewComment toEntity(User user, Review review, ReviewCommentCreateRequestDto requestDto) {
        return ReviewComment.builder()
                .content(requestDto.getContent())
                .review(review)
                .user(user)
                .build();
    }

    public List<ReviewCommentGetResponseDto> findReviewComments(Long wishId, Long reviewId) {
        Review review = reviewService.findByReviewId(reviewId);
        if (review.getWish().getId() != wishId) {
            throw new IllegalArgumentException("해당 리뷰를 조회할 수 없습니다.");
        }
        return reviewCommentRepository.findByReviewId(reviewId).stream()
                .map(reviewComment -> ReviewCommentGetResponseDto.builder()
                        .userId(reviewComment.getUser().getId())
                        .userName(reviewComment.getUser().getName())
                        .content(reviewComment.getContent())
                        .createdAt(reviewComment.getCreatedDate())
                        .build())
                .toList();

    }

}
