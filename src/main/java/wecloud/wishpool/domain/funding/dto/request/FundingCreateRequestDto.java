package wecloud.wishpool.domain.funding.dto.request;

import lombok.Getter;

@Getter
public class FundingCreateRequestDto {
    private Long amount;
    private String message;
}
