package wecloud.wishpool.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wecloud.wishpool.domain.user.dto.request.UserCreateRequestDto;
import wecloud.wishpool.domain.user.entity.User;
import wecloud.wishpool.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long createUser(UserCreateRequestDto requestDto) {
        return userRepository.save(toEntity(requestDto)).getId();
    }

    private User toEntity(UserCreateRequestDto requestDto) {
        return User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .birth(requestDto.getBirth())
                .build();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
    }
}
