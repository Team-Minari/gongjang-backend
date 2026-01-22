package com.teamminari.gongjang.user.repository;

import com.teamminari.gongjang.user.entity.OAuthProvider;
import com.teamminari.gongjang.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * User 저장소
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    // 이메일로 조회
    Optional<User> findByEmail(String email);
    
    // OAuth 제공자 + 제공자 ID로 조회 (카카오 로그인 시 사용)
    Optional<User> findByOauthProviderAndOauthProviderId(OAuthProvider provider, String providerId);
}