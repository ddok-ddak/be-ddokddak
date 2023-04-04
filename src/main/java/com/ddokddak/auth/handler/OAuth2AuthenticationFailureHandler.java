package com.ddokddak.auth.handler;

import com.ddokddak.auth.repository.OAuth2CookieAuthorizationRequestRepository;
import com.ddokddak.common.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final OAuth2CookieAuthorizationRequestRepository OAuth2AuthorizationRequestWithCookieRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        super.onAuthenticationFailure(request, response, exception);

        String targetUrl = CookieUtil.getCookie(request, OAuth2AuthorizationRequestWithCookieRepository.REDIRECT_URI_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(("/"));

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();

        OAuth2AuthorizationRequestWithCookieRepository.removeAuthorizationRequestCookies(request, response);
        getRedirectStrategy().sendRedirect(request, response, request.getParameter("redirect_uri"));
    }
}