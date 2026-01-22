package com.teamminari.gongjang.auth.service;

import com.teamminari.gongjang.auth.dto.response.TokenResponse;
import com.teamminari.gongjang.auth.dto.response.UserInfoResponse;
import com.teamminari.gongjang.auth.entity.RefreshToken;
import com.teamminari.gongjang.auth.repository.RefreshTokenRepository;
import com.teamminari.gongjang.global.exception.BusinessException;
import com.teamminari.gongjang.global.exception.ErrorCode;
import com.teamminari.gongjang.jwt.JwtProperties;
import com.teamminari.gongjang.jwt.JwtTokenProvider;
import com.teamminari.gongjang.oauth.KakaoOAuthClient;
import com.teamminari.gongjang.oauth.KakaoOAuthProperties;
import com.teamminari.gongjang.oauth.dto.KakaoTokenResponse;
import com.teamminari.gongjang.oauth.dto.KakaoUserInfoResponse;
import com.teamminari.gongjang.user.entity.OAuthProvider;
import com.teamminari.gongjang.user.entity.User;
import com.teamminari.gongjang.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 인증 관련 비즈니스 로직
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final KakaoOAuthClient kakaoOAuthClient;
    private final KakaoOAuthProperties kakaoOAuthProperties;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;

    // 카카오 로그인 URL 반환
    public String getKakaoLoginUrl() {
        return kakaoOAuthProperties.getLoginUrl();
    }

    // 카카오 로그인 처리
    @Transactional
    public TokenResponse kakaoLogin(String code) {
        // 1. 인가코드로 카카오 토큰 요청
        KakaoTokenResponse kakaoToken = kakaoOAuthClient.getToken(code);

        // 2. 카카오 토큰으로 사용자 정보 조회
        KakaoUserInfoResponse userInfo = kakaoOAuthClient.getUserInfo(kakaoToken.accessToken());

        // 3. 사용자 조회 또는 생성
        User user = findOrCreateUser(userInfo);

        // 4. JWT 토큰 발급
        return createTokenResponse(user.getId());
    }

    // 사용자 조회 또는 생성
    private User findOrCreateUser(KakaoUserInfoResponse userInfo) {
        return userRepository.findByOauthProviderAndOauthProviderId(
                OAuthProvider.KAKAO,
                String.valueOf(userInfo.id())
        ).orElseGet(() -> userRepository.save(
                User.builder()
                        .email(userInfo.kakaoAccount().email())
                        .name(userInfo.kakaoAccount().profile().nickname())
                        .profileImageUrl(userInfo.kakaoAccount().profile().profileImageUrl())
                        .oauthProvider(OAuthProvider.KAKAO)
                        .oauthProviderId(String.valueOf(userInfo.id()))
                        .build()
        ));
    }

    // JWT 토큰 생성 및 Refresh Token 저장
    private TokenResponse createTokenResponse(Long userId) {
        String accessToken = jwtTokenProvider.createAccessToken(userId);
        String refreshToken = jwtTokenProvider.createRefreshToken(userId);

        saveRefreshToken(userId, refreshToken);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtProperties.accessTokenValidity() / 1000)
                .build();
    }

    // Refresh Token 저장 (기존 토큰 있으면 갱신)
    private void saveRefreshToken(Long userId, String token) {
        LocalDateTime expiresAt = LocalDateTime.now()
                .plusSeconds(jwtProperties.refreshTokenValidity() / 1000);

        refreshTokenRepository.findByUserId(userId)
                .ifPresentOrElse(
                        existing -> existing.updateToken(token, expiresAt),
                        () -> refreshTokenRepository.save(
                                RefreshToken.builder()
                                        .userId(userId)
                                        .token(token)
                                        .expiresAt(expiresAt)
                                        .build()
                        )
                );
    }

    // Access Token 갱신
    @Transactional
    public TokenResponse refreshToken(String refreshToken) {
        // DB에서 Refresh Token 조회
        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new BusinessException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));

        // 만료 확인
        if (storedToken.isExpired()) {
            refreshTokenRepository.delete(storedToken);
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        // 새 토큰 발급
        return createTokenResponse(storedToken.getUserId());
    }

    // 로그아웃 (Refresh Token 삭제)
    @Transactional
    public void logout(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    // 현재 사용자 정보 조회
    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return UserInfoResponse.from(user);
    }
}
