package com.ddokddak.member.service;

import com.ddokddak.member.domain.entity.OAuth2Member;
import com.ddokddak.member.repository.OAuth2MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OAuth2MemberReadService {

    private final OAuth2MemberRepository oauth2MemberRepository;

    public OAuth2Member findByMemberId(Long memberId) {
        return oauth2MemberRepository
                .findByMemberId(memberId)
                .orElse(null);
    }
}
