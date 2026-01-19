package com.teamminari.gongjang.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        int statusCode,
        T data,
        String message
) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), data, null);
    }

    public static <T> ApiResponse<T> ok(T data, String message) {
        return new ApiResponse<>(HttpStatus.OK.value(), data, message);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), data, "리소스가 성공적으로 생성되었습니다.");
    }

    public static <T> ApiResponse<T> created(T data, String message) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), data, message);
    }

    public static ApiResponse<Void> noContent() {
        return new ApiResponse<>(HttpStatus.NO_CONTENT.value(), null, null);
    }

    public static <T> ApiResponse<T> of(HttpStatus status, T data, String message) {
        return new ApiResponse<>(status.value(), data, message);
    }
}
