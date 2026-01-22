package com.teamminari.gongjang.oauth;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * application.yml의 Kakao OAuth 설정 정보를 매핑하는 클래스
 */
@ConfigurationProperties(prefix = "oauth.kakao")
public record KakaoOAuthProperties(
        String clientId,       // Kakao REST API 키
        String clientSecret,   // Kakao Client Secret
        String redirectUri     // 인가코드 받을 콜백 URL
) {
    private static final String AUTHORIZATION_URI = "https://kauth.kakao.com/oauth/authorize";

    // Kakao 로그인 페이지 URL 생성
    public String getLoginUrl() {
        return AUTHORIZATION_URI +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code";
    }
}
