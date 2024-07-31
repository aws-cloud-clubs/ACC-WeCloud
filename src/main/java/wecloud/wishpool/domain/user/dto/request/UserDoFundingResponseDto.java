package wecloud.wishpool.domain.user.dto.request;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
public class UserDoFundingResponseDto {
    private Long id;
    private String name;
}
