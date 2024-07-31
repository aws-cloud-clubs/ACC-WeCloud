package wecloud.wishpool.domain.wish.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wecloud.wishpool.domain.user.entity.User;
import wecloud.wishpool.domain.user.service.UserService;
import wecloud.wishpool.domain.wish.dto.request.WishSaveRequestDto;
import wecloud.wishpool.domain.wish.dto.request.WishUpdateRequestDto;
import wecloud.wishpool.domain.wish.dto.response.WishGetOneResponseDto;
import wecloud.wishpool.domain.wish.entity.Wish;
import wecloud.wishpool.domain.wish.repository.WishRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishService {
    private final WishRepository wishRepository;
    private final UserService userService;

    @Transactional
    public Wish createWish(Long userId, WishSaveRequestDto requestDto) {
        User findUser = userService.findByUserId(userId);
        return wishRepository.save(toEntity(findUser, requestDto));
    }


    private Wish toEntity(User user,WishSaveRequestDto requestDto) {
        return Wish.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .deadline(requestDto.getDeadline())
                .targetAmount(requestDto.getTargetAmount())
                .user(user)
                .build();
    }

    public WishGetOneResponseDto findWish(Long wishId) {
        Wish wish = findByWishId(wishId);
        WishGetOneResponseDto responseDto = WishGetOneResponseDto.builder()
                .title(wish.getTitle())
                .content(wish.getContent())
                .image(wish.getImage())
                .deadline(wish.getDeadline())
                .targetAmount(wish.getTargetAmount())
                .currentAmount(wish.getCurrentAmount())
                .isCompleted(wish.isCompleted())
                .isEnded(wish.isEnded())
                .build();
        return responseDto;
    }

    @Transactional
    public WishGetOneResponseDto updateWish(Long wishId, WishUpdateRequestDto requestDto) {
        Wish wish = findByWishId(wishId);
        wish.update(requestDto.getTitle(),requestDto.getContent(),requestDto.getImage(),requestDto.getTargetAmount());
        WishGetOneResponseDto responseDto = WishGetOneResponseDto.builder()
                .title(wish.getTitle())
                .content(wish.getContent())
                .image(wish.getImage())
                .deadline(wish.getDeadline())
                .targetAmount(wish.getTargetAmount())
                .currentAmount(wish.getCurrentAmount())
                .build();
        return responseDto;
    }

    public Wish findByWishId(Long wishId) {
        return wishRepository.findByIdAndIsDeletedFalse(wishId).orElseThrow(() -> new IllegalArgumentException("해당 소원이 없습니다."));
    }

    public Wish findByWishIdForFunding(Long wishId) {
        Wish wish = wishRepository.findByIdAndIsDeletedFalse(wishId).orElseThrow(() -> new IllegalArgumentException("해당 소원이 없습니다."));
        if (wish.isCompleted()) {
            throw new IllegalArgumentException("펀딩이 완료된 소원입니다.");
        } else if (wish.isEnded()) {
            throw new IllegalArgumentException("마감된 소원입니다.");
        }
        return wish;
    }

    @Transactional
    public void deleteWish(Long wishId) {
        Wish wish = findByWishId(wishId);
        wish.delete();
    }

    @Transactional
    public void closeWish(Long wishId) {
        Wish wish = findByWishId(wishId);
        wish.updateEnd();
    }

    @Transactional
    public void completeWish(Long wishId) {
        Wish wish = findByWishId(wishId);
        wish.updateCompleted();
    }

    @Transactional
    public void updateImage(Long id, String fileName) {
        Wish wish = findByWishId(id);
        wish.updateImage(fileName);
    }
}
