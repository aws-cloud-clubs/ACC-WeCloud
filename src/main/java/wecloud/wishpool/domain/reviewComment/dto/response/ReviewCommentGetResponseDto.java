package wecloud.wishpool.domain.reviewComment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReviewCommentGetResponseDto {
    private Long userId;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
}
