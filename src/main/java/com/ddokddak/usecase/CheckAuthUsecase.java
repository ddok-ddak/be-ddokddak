package com.ddokddak.usecase;

import com.ddokddak.auth.domain.dto.SigningRequest;
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

    @Transactional(noRollbackFor = BadCredentialsException.class)
    public Authentication getAuthentication(SigningRequest signingRequest) {
        Authentication authentication;
        try {
            // 크레덴셜 지워짐
            authentication = authenticationManagerBuilder.getObject()
                    .authenticate(new UsernamePasswordAuthenticationToken(signingRequest.email(), signingRequest.password()));
        } catch (BadCredentialsException badCredentialsException) {
            memberWriteService.countFailedPasswordTry(signingRequest.email());
            throw badCredentialsException;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
