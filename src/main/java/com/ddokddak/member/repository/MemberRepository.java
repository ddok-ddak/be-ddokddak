package com.ddokddak.member.repository;

import com.ddokddak.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String name);
    Optional<Member> findByEmail(String email);
    Optional<Member> findMemberById(Long memberId);
}
