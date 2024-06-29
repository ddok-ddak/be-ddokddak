package com.ddokddak.member.service;

import com.ddokddak.member.domain.entity.AuthToken;
import com.ddokddak.member.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthTokenReadService {
    private final AuthTokenRepository authTokenRepository;

    @Transactional
    public AuthToken findByMemberId(Long memberId) {
        return authTokenRepository.findByMemberId(memberId);

    }
}
