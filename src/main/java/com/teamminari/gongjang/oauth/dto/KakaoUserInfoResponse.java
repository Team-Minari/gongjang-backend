package com.teamminari.gongjang.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 카카오 사용자 정보 API 응답을 매핑하는 DTO
 * (카카오 Access Token으로 사용자 정보 조회 시 카카오가 주는 응답)
 */
public record KakaoUserInfoResponse(
        @JsonProperty("id")
        Long id,                         // 카카오 회원번호

        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount        // 카카오 계정 정보
) {
    public record KakaoAccount(
            @JsonProperty("email")
            String email,

            @JsonProperty("profile")
            Profile profile
    ) {
        public record Profile(
                @JsonProperty("nickname")
                String nickname,

                @JsonProperty("profile_image_url")
                String profileImageUrl
        ) {}
    }
}

