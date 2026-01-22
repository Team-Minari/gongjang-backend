package com.teamminari.gongjang.auth.dto.response;

import com.teamminari.gongjang.user.entity.User;
import lombok.Builder;

/**
 * 사용자 정보 응답 DTO (/auth/me 에서 사용)
 */
@Builder
public record UserInfoResponse(
        Long id,
        String email,
        String name,
        String profileImageUrl
) {
    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
