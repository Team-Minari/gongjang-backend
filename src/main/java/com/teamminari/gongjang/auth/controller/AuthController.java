package com.teamminari.gongjang.auth.controller;

import com.teamminari.gongjang.auth.dto.request.RefreshTokenRequest;
import com.teamminari.gongjang.auth.dto.response.TokenResponse;
import com.teamminari.gongjang.auth.dto.response.UserInfoResponse;
import com.teamminari.gongjang.auth.service.AuthService;
import com.teamminari.gongjang.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 인증 관련 API
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 카카오 로그인 URL 반환
    @GetMapping("/kakao/login-url")
    public ResponseEntity<ApiResponse<Map<String, String>>> getKakaoLoginUrl() {
        String loginUrl = authService.getKakaoLoginUrl();
        return ResponseEntity.ok(ApiResponse.success(Map.of("loginUrl", loginUrl)));
    }

    // 카카오 콜백 (카카오에서 리다이렉트)
    @GetMapping("/kakao/callback")
    public ResponseEntity<ApiResponse<TokenResponse>> kakaoCallback(@RequestParam String code) {
        TokenResponse response = authService.kakaoLogin(code);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 카카오 로그인 (프론트에서 직접 호출 시)
    @PostMapping("/kakao/login")
    public ResponseEntity<ApiResponse<TokenResponse>> kakaoLogin(@RequestParam String code) {
        TokenResponse response = authService.kakaoLogin(code);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // Access Token 갱신
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {
        TokenResponse response = authService.refreshToken(request.refreshToken());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@AuthenticationPrincipal Long userId) {
        authService.logout(userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    // 현재 사용자 정보 조회
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getMe(@AuthenticationPrincipal Long userId) {
        UserInfoResponse response = authService.getUserInfo(userId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
