package wecloud.wishpool.domain.wish.dto.request;

import lombok.Getter;

@Getter
public class WishUpdateRequestDto {
    private String title;
    private String content;
    private String image;
    private Long targetAmount;
}
