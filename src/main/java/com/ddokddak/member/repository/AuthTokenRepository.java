package com.ddokddak.member.repository;

import com.ddokddak.member.domain.entity.AuthToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthTokenRepository extends JpaRepository<AuthToken, Long> {
    AuthToken findByMemberId(Long memberId);
    void deleteByMemberId(Long memberId);
}
