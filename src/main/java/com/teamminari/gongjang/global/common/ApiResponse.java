package com.teamminari.gongjang.global.common;

public record ApiResponse<T>(
        int statusCode,
        T data,
        String message
) {
}
