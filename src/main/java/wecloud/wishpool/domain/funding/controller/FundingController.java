package wecloud.wishpool.domain.funding.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wecloud.wishpool.domain.funding.dto.request.FundingCreateRequestDto;
import wecloud.wishpool.domain.funding.dto.response.FundingGetOneResponseDto;
import wecloud.wishpool.domain.funding.service.FundingService;
import wecloud.wishpool.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishes/{wishId}/fundings")
@Tag(name = "Funding API", description = "펀딩 API")
public class FundingController {
    private final FundingService fundingService;

    @PostMapping
    @Operation(summary = "펀딩 생성", description = "펀딩을 생성합니다.")
    public ApiResponse<Long> createFunding(@RequestParam Long userId,@PathVariable Long wishId, @RequestBody FundingCreateRequestDto requestDto) {
        return ApiResponse.response201Success(fundingService.createFunding(userId,wishId,requestDto), "펀딩 생성 완료");
    }

    @GetMapping("/{fundingId}")
    @Operation(summary = "펀딩 조회", description = "펀딩을 조회합니다.")
    public ApiResponse<FundingGetOneResponseDto> getFunding(@PathVariable Long fundingId,@PathVariable Long wishId, @RequestParam Long userId) {
        FundingGetOneResponseDto fundingDto = fundingService.findFunding(fundingId,userId,wishId);
        return ApiResponse.responseSuccess(fundingDto, "펀딩 조회 완료");
    }
}
