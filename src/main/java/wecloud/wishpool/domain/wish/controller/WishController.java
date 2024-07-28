package wecloud.wishpool.domain.wish.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wecloud.wishpool.domain.wish.dto.request.WishSaveRequestDto;
import wecloud.wishpool.domain.wish.dto.request.WishUpdateRequestDto;
import wecloud.wishpool.domain.wish.dto.response.WishGetOneResponseDto;
import wecloud.wishpool.domain.wish.entity.Wish;
import wecloud.wishpool.domain.wish.service.WishService;
import wecloud.wishpool.global.response.ApiResponse;

@Tag(name = "Wish API", description = "소원 피드 API")
@RestController
@RequestMapping("/wishes")
@RequiredArgsConstructor
public class WishController {
    private final WishService wishService;
    @PostMapping
    @Operation(summary = "소원 피드 생성", description = "소원 피드를 생성합니다.")
    public ApiResponse<Long> createWish(@RequestParam Long userId, @RequestBody WishSaveRequestDto requestDto) {
        return ApiResponse.response201Success(wishService.createWish(userId,requestDto), "소원 피드 생성 완료");
    }

    @GetMapping("/{wishId}")
    @Operation(summary = "소원 피드 조회", description = "소원 피드를 조회합니다.")
    public ApiResponse<WishGetOneResponseDto> getWish(@PathVariable Long wishId) {
        WishGetOneResponseDto wishDto = wishService.findById(wishId);
        return ApiResponse.responseSuccess(wishDto, "소원 피드 조회 완료");
    }

    @PutMapping("/{wishId}")
    @Operation(summary = "소원 피드 수정", description = "소원 피드를 수정합니다.")
    public ApiResponse<WishGetOneResponseDto> updateWish(@PathVariable Long wishId, @RequestBody WishUpdateRequestDto requestDto) {
        WishGetOneResponseDto wishDto = wishService.updateWish(wishId,requestDto);
        return ApiResponse.responseSuccess(wishDto, "소원 피드 수정 완료");
    }

    @DeleteMapping("/{wishId}")
    @Operation(summary = "소원 피드 삭제", description = "소원 피드를 삭제합니다.")
    public ApiResponse<?> deleteWish(@PathVariable Long wishId) {
        wishService.deleteWish(wishId);
        return ApiResponse.responseSuccess("소원 피드 삭제 완료");
    }



}
