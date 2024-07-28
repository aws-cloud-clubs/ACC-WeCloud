package wecloud.wishpool.global.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private boolean isSuccess;
    private StatusEnum status;
    private T data;
    private String message;

    public static <T> ApiResponse<T> responseSuccess(T data) {
        return new ApiResponse<>(true, StatusEnum.OK, data, null);
    }

    public static <T> ApiResponse<T> responseSuccess(T data, String message) {
        return new ApiResponse<>(true, StatusEnum.OK, data, message);
    }

    public static <T> ApiResponse<T> response201Success(T data, String message) {
        return new ApiResponse<>(true, StatusEnum.CREATED, data, message);
    }

    // 예외 발생으로 API 호출 실패시 반환
    public static ApiResponse<?> response400Error(String message) {
        return new ApiResponse<>(false, StatusEnum.BAD_REQUEST, null, message);
    }

    @Builder
    private ApiResponse(boolean isSuccess, StatusEnum status, T data, String message) {
        this.isSuccess = isSuccess;
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
