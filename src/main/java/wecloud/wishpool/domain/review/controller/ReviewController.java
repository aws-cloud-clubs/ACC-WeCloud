package wecloud.wishpool.domain.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wecloud.wishpool.domain.review.dto.request.ReviewCreateRequestDto;
import wecloud.wishpool.domain.review.dto.response.ReviewGetResponseDto;
import wecloud.wishpool.domain.review.service.ReviewService;
import wecloud.wishpool.global.response.ApiResponse;

@Tag(name = "Review API", description = "리뷰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wishes/{wishId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping
    @Operation(summary = "리뷰 생성", description = "리뷰를 생성합니다.")
    public ApiResponse<Long> createReview(@PathVariable Long wishId, @RequestBody ReviewCreateRequestDto requestDto) {
        Long reviewId = reviewService.createReview(wishId, requestDto);
        return ApiResponse.response201Success(reviewId, "리뷰 생성 완료");
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "리뷰 조회", description = "리뷰를 조회합니다.")
    public ApiResponse<ReviewGetResponseDto> getReview(@PathVariable Long wishId, @PathVariable Long reviewId) {
        ReviewGetResponseDto reviewDto = reviewService.findReview(reviewId, wishId);
        return ApiResponse.responseSuccess(reviewDto, "리뷰 조회 완료");
    }

}
