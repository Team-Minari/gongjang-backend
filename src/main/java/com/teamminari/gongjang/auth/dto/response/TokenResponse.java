package com.teamminari.gongjang.auth.dto.response;

import lombok.Builder;

/**
 * JWT 토큰 응답 DTO (프론트에 전달)
 */
@Builder
public record TokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn  // Access Token 만료시간 (초)
) {}
