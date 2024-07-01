package com.ddokddak.member.repository;

import com.ddokddak.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String name);
    Optional<Member> findByEmail(String email);
    Optional<Member> findMemberById(Long memberId);

}
