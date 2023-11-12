package com.ddokddak.member.repository;

import com.ddokddak.member.domain.entity.Oauth2Member;
import com.ddokddak.member.domain.enums.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Oauth2MemberRepository extends JpaRepository<Oauth2Member, Long> {

    public Optional<Oauth2Member> findByEmailAndAuthProviderAndIsDeletedFalse(String oauth2Id, AuthProviderType authProvider);

}
