package com.teamminari.gongjang.oauth;

import com.teamminari.gongjang.global.exception.BusinessException;
import com.teamminari.gongjang.global.exception.ErrorCode;
import com.teamminari.gongjang.oauth.dto.KakaoTokenResponse;
import com.teamminari.gongjang.oauth.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

/**
 * 카카오 OAuth API를 호출하는 클라이언트
 */
@Component
@RequiredArgsConstructor
public class KakaoOAuthClient {

    // 카카오 공식 API URL
    private static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private final KakaoOAuthProperties properties;
    private final RestClient restClient;

    // 인가코드 → 카카오 토큰 교환
    public KakaoTokenResponse getToken(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", properties.clientId());
        params.add("client_secret", properties.clientSecret());
        params.add("redirect_uri", properties.redirectUri());
        params.add("code", code);

        try {
            return restClient.post()
                    .uri(TOKEN_URL)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(params)
                    .retrieve()
                    .body(KakaoTokenResponse.class);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OAUTH_CODE_INVALID);
        }
    }

    // 카카오 토큰 → 사용자 정보 조회
    public KakaoUserInfoResponse getUserInfo(String accessToken) {
        try {
            return restClient.get()
                    .uri(USER_INFO_URL)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .body(KakaoUserInfoResponse.class);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OAUTH_PROVIDER_ERROR);
        }
    }
}
