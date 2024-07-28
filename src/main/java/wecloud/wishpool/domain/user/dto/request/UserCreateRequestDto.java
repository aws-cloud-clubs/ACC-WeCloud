package wecloud.wishpool.domain.user.dto.request;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class UserCreateRequestDto {
    private String name;
    private String email;
    private String password;
    private LocalDate birth;
}
