package com.ddokddak.member.repository;

import com.ddokddak.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberById(Long memberId);
}
