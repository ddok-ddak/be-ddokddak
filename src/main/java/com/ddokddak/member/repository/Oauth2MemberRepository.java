package com.ddokddak.member.repository;

import com.ddokddak.member.entity.Member;
import com.ddokddak.member.entity.Oauth2Member;
import com.ddokddak.member.entity.enums.AuthProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface Oauth2MemberRepository extends JpaRepository<Oauth2Member, Long> {

    public Optional<Oauth2Member> findByOauth2IdAndAuthProviderAndIsDeletedFalse(String oauth2Id, AuthProviderType authProvider);

}
