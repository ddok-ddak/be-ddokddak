package com.ddokddak.auth.filter;

import com.ddokddak.common.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${spring.profiles.active}")
    private String activeProfile;
    private final Set<String> profiles = Set.of("dev", "test", "local");
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)  throws ServletException, IOException {

        String token = parseBearerToken(request);

        // todo 개발용, 제거 혹은 조건 추가
        if (this.profiles.contains(activeProfile) &&  token == null) {
            token = jwtUtil.createAccessTokenForDev();
        }

        if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
            Authentication authentication = jwtUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug(">>>>>>>>>>> Set Authentication of : {} <<<<<<<<<<<", authentication.getName());
        } else {
            String requestURI = request.getRequestURI();
            log.debug(">>>>>>>>>>> No Valid JWT, uri: {} <<<<<<<<<<<", requestURI);
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtUtil.AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}