package wecloud.wishpool.domain.wish.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WishGetOneResponseDto {
    private String title;
    private String content;
    private String image;
    private LocalDateTime deadline;
    private Long targetAmount;
    private Long currentAmount;
}
