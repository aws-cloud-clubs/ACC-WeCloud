package wecloud.wishpool.domain.wish.dto.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WishSaveRequestDto {
    private String title;
    private String content;
    private LocalDateTime deadline;
    private Long targetAmount;
}
