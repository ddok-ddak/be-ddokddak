package com.ddokddak.usecase;

import com.ddokddak.auth.domain.dto.SigningRequest;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.type.MemberException;
import com.ddokddak.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckAuthUsecase {

    private final MemberWriteService memberWriteService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional(noRollbackFor = {BadCredentialsException.class, CustomApiException.class})
    public Authentication getAuthentication(SigningRequest signingRequest) {
        Authentication authentication;
        try {
            // 크레덴셜 지워짐
            authentication = authenticationManagerBuilder.getObject()
                    .authenticate(new UsernamePasswordAuthenticationToken(signingRequest.email(), signingRequest.password()));
        } catch (BadCredentialsException badCredentialsException) {
            int failedPasswordCount = memberWriteService.countFailedPasswordTry(signingRequest.email());
            if (failedPasswordCount == 5) {
                throw new CustomApiException(MemberException.LOCKED_MEMBER);
            }
            throw new CustomApiException(MemberException.FAILED_ID_PASSWORD);
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
