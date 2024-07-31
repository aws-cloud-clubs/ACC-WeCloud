package wecloud.wishpool.domain.reviewComment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wecloud.wishpool.domain.reviewComment.dto.request.ReviewCommentCreateRequestDto;
import wecloud.wishpool.domain.reviewComment.dto.response.ReviewCommentGetResponseDto;
import wecloud.wishpool.domain.reviewComment.service.ReviewCommentService;
import wecloud.wishpool.global.response.ApiResponse;

import java.util.List;

@Tag(name = "ReviewComment API", description = "리뷰 댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wishes/{wishId}/reviews/{reviewId}/comments")
public class ReviewCommentController {
    private final ReviewCommentService reviewCommentService;
    @PostMapping
    @Operation(summary = "리뷰 댓글 생성", description = "리뷰에 댓글을 생성합니다.")
    public ApiResponse<Long> createReviewComment(@PathVariable Long wishId, @PathVariable Long reviewId, @RequestParam Long userId ,@RequestBody ReviewCommentCreateRequestDto requestDto) {
        Long reviewCommentId = reviewCommentService.createReviewComment(wishId, reviewId, userId, requestDto);
        return ApiResponse.response201Success(reviewCommentId, "리뷰 댓글 생성 완료");
    }

    @GetMapping
    @Operation(summary = "리뷰 댓글 조회", description = "리뷰 댓글을 조회합니다.")
    public ApiResponse<List<ReviewCommentGetResponseDto>> getReviewComments(@PathVariable Long wishId, @PathVariable Long reviewId) {
        List<ReviewCommentGetResponseDto> reviewCommentDto = reviewCommentService.findReviewComments(wishId, reviewId);
        return ApiResponse.responseSuccess(reviewCommentDto, "리뷰 댓글 조회 완료");
    }

}
