package wecloud.wishpool.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wecloud.wishpool.domain.user.dto.request.UserCreateRequestDto;
import wecloud.wishpool.domain.user.service.UserService;
import wecloud.wishpool.global.response.ApiResponse;

@Tag(name = "User API", description = "유저 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @PostMapping
    @Operation(summary = "유저 생성", description = "유저를 생성합니다.")
    public ApiResponse<Long> createUser(@RequestBody UserCreateRequestDto requestDto) {
        return ApiResponse.response201Success(userService.createUser(requestDto), "유저 생성 완료");
    }
}
