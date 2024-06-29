package com.ddokddak.member.service;

import com.ddokddak.common.dto.TokenInfo;
import com.ddokddak.member.domain.entity.AuthToken;
import com.ddokddak.member.repository.AuthTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthTokenWriteService {

    private final AuthTokenRepository authTokenRepository;

    @Transactional
    public AuthToken saveTokenInfo(Long memberId, TokenInfo refreshToken) {
        AuthToken authToken = authTokenRepository.findByMemberId(memberId);
        if (authToken != null) {
            authToken.modifyRefreshToken(refreshToken);
        } else {
            authToken = AuthToken.builder()
                    .memberId(memberId)
                    .refreshToken(refreshToken.getToken())
                    .refreshTokenIssuedAt(refreshToken.getIssuedAt())
                    .refreshTokenExpiredAt(refreshToken.getExpiredAt())
                    .build();
        }
        return authToken;
    }

    @Transactional
    public void removeAuthTokenByMemberId(Long memberId) {
        authTokenRepository.deleteByMemberId(memberId);
    }
}