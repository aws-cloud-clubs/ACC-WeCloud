package wecloud.wishpool.domain.funding.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FundingGetOneResponseDto {
    private Long id;
    private Long amount;
    private String message;
}
