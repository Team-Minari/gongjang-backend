package com.teamminari.gongjang.global.common;

public record ApiResponse<T>(
        int statusCode,
        T data,
        String message
) {
    // 성공 응답 (데이터 포함)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, data, "Success");
    }

    // 성공 응답 (메시지 커스텀)
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(200, data, message);
    }
}
