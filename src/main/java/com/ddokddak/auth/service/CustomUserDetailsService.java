package com.ddokddak.auth.service;

import com.ddokddak.auth.domain.oauth.UserPrincipal;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.member.domain.enums.Status;
import com.ddokddak.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<UserPrincipal> userPrincipal = memberRepository.findByEmail(email)
                .map(user -> {
                    if (user.getStatus() == Status.NORMAL) {
                        return UserPrincipal.create(user, null);
                    } else {
                        throw new CustomApiException(email + "-> 활성화되어 있지 않습니다.");
                    }
                });
        return userPrincipal.orElseThrow(() -> new UsernameNotFoundException(email + "-> 데이터베이스에서 찾을 수 없습니다."));
    }
}
