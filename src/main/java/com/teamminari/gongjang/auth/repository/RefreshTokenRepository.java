package com.teamminari.gongjang.auth.repository;

import com.teamminari.gongjang.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Refresh Token 저장소
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    // 토큰 값으로 조회
    Optional<RefreshToken> findByToken(String token);
    
    // 유저 ID로 조회
    Optional<RefreshToken> findByUserId(Long userId);
    
    // 유저 ID로 삭제 (로그아웃 시)
    void deleteByUserId(Long userId);
}
