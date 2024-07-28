package wecloud.wishpool.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import wecloud.wishpool.global.response.ApiResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse illegalArgExHandle(IllegalArgumentException e) {
        log.error("[illegalArgExHandle]", e);
        return ApiResponse.response400Error(e.getMessage());
    }
}
