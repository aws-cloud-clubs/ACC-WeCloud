package wecloud.wishpool.domain.review.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewGetResponseDto {
    private Long id;
    private String content;
    private String image;
}
