package com.teamminari.gongjang.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * application.yml의 jwt 설정 정보를 매핑하는 클래스
 */
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String secret,                // JWT 서명용 비밀키
        long accessTokenValidity,     // Access Token 만료시간 (ms)
        long refreshTokenValidity     // Refresh Token 만료시간 (ms)
) {
}
