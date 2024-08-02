package wecloud.wishpool.domain.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wecloud.wishpool.domain.review.dto.request.ReviewCreateRequestDto;
import wecloud.wishpool.domain.review.dto.response.ReviewGetResponseDto;
import wecloud.wishpool.domain.review.service.ReviewService;
import wecloud.wishpool.global.aws.service.S3Service;
import wecloud.wishpool.global.response.ApiResponse;

import java.io.IOException;

@Tag(name = "Review API", description = "리뷰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/wishes/{wishId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final S3Service s3Service;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "리뷰 생성", description = "리뷰를 생성합니다.")
    public ApiResponse<Long> createReview(@PathVariable Long wishId,
                                          @RequestPart ReviewCreateRequestDto requestDto,
                                          @RequestPart(value = "img", required = false) MultipartFile multipartFile) throws IOException {
        String reviewImg = s3Service.upload(multipartFile, "review");
        Long reviewId = reviewService.createReview(wishId,reviewImg, requestDto);
        return ApiResponse.response201Success(reviewId, "리뷰 생성 완료");
    }

    @GetMapping("/{reviewId}")
    @Operation(summary = "리뷰 조회", description = "리뷰를 조회합니다.")
    public ApiResponse<ReviewGetResponseDto> getReview(@PathVariable Long wishId, @PathVariable Long reviewId) {
        ReviewGetResponseDto reviewDto = reviewService.findReview(reviewId, wishId);
        return ApiResponse.responseSuccess(reviewDto, "리뷰 조회 완료");
    }

}
