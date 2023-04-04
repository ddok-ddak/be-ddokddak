package com.ddokddak.member.service;

import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.member.dto.MemberResponse;
import com.ddokddak.member.entity.Member;
import com.ddokddak.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberReadService {

    private final MemberRepository memberRepository;

    public Member findUserById(Long userId) {
        return memberRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("찾는 사용자가 존재하지 않습니다."));
    }

    @Transactional(readOnly = true)
    public Member findUserByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Not Registered User Mail"));
    }

    @Transactional(readOnly = true)
    public MemberResponse getUserDtoByEmail(String email) {
        var member = this.findUserByEmail(email);
        return MemberResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }

    @Transactional(readOnly = true)
    public void checkIfDuplicatedUserByNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new CustomApiException(
                    "Already Exists User with Nickname", HttpStatus.CONFLICT);
        }
    }

    @Transactional(readOnly = true)
    public void checkIfDuplicatedUserByEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            throw new CustomApiException(
                    "Already Exists User Mail with " + member.get().getAuthProviderType(), HttpStatus.CONFLICT);
        }
    }

    public boolean existsUserByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}
