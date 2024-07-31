package wecloud.wishpool.domain.funding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wecloud.wishpool.domain.funding.dto.request.FundingCreateRequestDto;
import wecloud.wishpool.domain.funding.dto.response.FundingGetOneResponseDto;
import wecloud.wishpool.domain.funding.entity.Funding;
import wecloud.wishpool.domain.funding.repository.FundingRepository;
import wecloud.wishpool.domain.user.entity.User;
import wecloud.wishpool.domain.user.service.UserService;
import wecloud.wishpool.domain.wish.entity.Wish;
import wecloud.wishpool.domain.wish.service.WishService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FundingService {
    private final FundingRepository fundingRepository;
    private final UserService userService;
    private final WishService wishService;

    @Transactional
    public Long createFunding(Long userId, Long wishId, FundingCreateRequestDto requestDto) {
        Wish wish = wishService.findByWishId(wishId);
        User user = userService.findByUserId(userId);
        wish.addCurrentAmount(requestDto.getAmount());
        return fundingRepository.save(toEntity(user, wish, requestDto)).getId();
    }

    private Funding toEntity(User user, Wish wish, FundingCreateRequestDto requestDto) {
        return Funding.builder()
                .amount(requestDto.getAmount())
                .message(requestDto.getMessage())
                .user(user)
                .wish(wish)
                .build();
    }


    public FundingGetOneResponseDto findFunding(Long fundingId,Long userId, Long wishId) {
        Funding funding = findByFundingId(fundingId);
        if(funding.getUser().getId() != userId || funding.getWish().getId() != wishId){
            throw new IllegalArgumentException("해당 펀딩을 조회할 수 없습니다.");
        }
        FundingGetOneResponseDto responseDto = FundingGetOneResponseDto.builder()
                .id(funding.getId())
                .amount(funding.getAmount())
                .message(funding.getMessage())
                .build();
        return responseDto;

    }

    public Funding findByFundingId(Long fundingId) {
        return fundingRepository.findById(fundingId).orElseThrow(() -> new IllegalArgumentException("해당 펀딩이 없습니다."));
    }
}
