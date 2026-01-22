package com.teamminari.gongjang.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 카카오 토큰 발급 API 응답을 매핑하는 DTO
 * (인가코드 → 토큰 교환 시 카카오가 주는 응답)
 */
public record KakaoTokenResponse(
        @JsonProperty("access_token")
        String accessToken,              // 카카오 Access Token

        @JsonProperty("token_type")
        String tokenType,                // "bearer"

        @JsonProperty("refresh_token")
        String refreshToken,             // 카카오 Refresh Token

        @JsonProperty("expires_in")
        Integer expiresIn,               // Access Token 만료시간 (초)

        @JsonProperty("refresh_token_expires_in")
        Integer refreshTokenExpiresIn    // Refresh Token 만료시간 (초)
) {}
