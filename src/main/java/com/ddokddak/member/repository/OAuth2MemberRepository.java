package com.ddokddak.member.repository;

import com.ddokddak.member.domain.entity.OAuth2Member;
import com.ddokddak.member.domain.enums.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OAuth2MemberRepository extends JpaRepository<OAuth2Member, Long> {

    public Optional<OAuth2Member> findByEmailAndAuthProviderAndIsDeletedFalse(String oauth2Id, AuthProviderType authProvider);

    Optional<OAuth2Member> findByOauth2Id(String oauth2Id);
    Optional<OAuth2Member> findByMemberId(Long memberId);
}
