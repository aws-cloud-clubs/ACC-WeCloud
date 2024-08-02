package wecloud.wishpool.domain.funding.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wecloud.wishpool.domain.funding.dto.request.FundingCreateRequestDto;
import wecloud.wishpool.domain.funding.dto.response.FundingGetOneResponseDto;
import wecloud.wishpool.domain.funding.entity.Funding;
import wecloud.wishpool.domain.funding.repository.FundingRepository;
import wecloud.wishpool.domain.sns.service.WishNotificationService;
import wecloud.wishpool.domain.user.dto.request.UserDoFundingResponseDto;
import wecloud.wishpool.domain.user.entity.User;
import wecloud.wishpool.domain.user.service.UserService;
import wecloud.wishpool.domain.wish.entity.Wish;
import wecloud.wishpool.domain.wish.service.WishService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FundingService {
    private final FundingRepository fundingRepository;
    private final UserService userService;
    private final WishService wishService;
    private final WishNotificationService wishNotificationService;

    @Transactional
    public Long createFunding(Long userId, Long wishId, FundingCreateRequestDto requestDto) {
        Wish wish = wishService.findByWishIdFunding(wishId);
        User user = userService.findByUserId(userId);
        wish.addCurrentAmount(requestDto.getAmount());
       fundingRepository.save(toEntity(user, wish, requestDto));
        if(wish.isCompleted()){
            List<String> userFundingEmail = getUserFundingEmail(wishId);
            userFundingEmail.add(wish.getUser().getEmail());

            wishNotificationService.notifyParticipants(wish,userFundingEmail);
        }
        return wish.getId();
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

    public List<FundingGetOneResponseDto> findFundings(Long wishId) {
        List<Funding> fundings = fundingRepository.findByWishId(wishId);
        return fundings.stream()
                .map(funding -> FundingGetOneResponseDto.builder()
                        .id(funding.getId())
                        .amount(funding.getAmount())
                        .message(funding.getMessage())
                        .build())
                .toList();
    }

    public List<UserDoFundingResponseDto> getUserFundingList(Long wishId) {
        List<Funding> fundings = fundingRepository.findByWishId(wishId);
        Set<UserDoFundingResponseDto> usersDto = fundings.stream()
                .map(funding -> UserDoFundingResponseDto.builder()
                        .id(funding.getUser().getId())
                        .name(funding.getUser().getName())
                        .build())
                .collect(Collectors.toSet());
        return usersDto.stream().toList();
    }

    public List<String> getUserFundingEmail(Long wishId){
        List<Funding> byWishId = fundingRepository.findByWishId(wishId);
        List<String> collect = byWishId.stream().map(funding -> funding.getUser().getEmail()).collect(Collectors.toList());
        return collect;
    }
}
