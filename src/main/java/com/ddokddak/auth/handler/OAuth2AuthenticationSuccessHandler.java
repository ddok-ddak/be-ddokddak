package com.ddokddak.auth.handler;

import com.ddokddak.auth.domain.oauth.UserPrincipal;
import com.ddokddak.auth.repository.OAuth2CookieAuthorizationRequestRepository;
import com.ddokddak.common.dto.TokenInfo;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.props.AppProperties;
import com.ddokddak.common.props.AuthProperties;
import com.ddokddak.common.utils.CookieUtil;
import com.ddokddak.common.utils.JwtUtil;
import com.ddokddak.member.domain.entity.AuthToken;
import com.ddokddak.member.service.AuthTokenReadService;
import com.ddokddak.member.service.AuthTokenWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthProperties authProperties;
    private final AppProperties appProperties;
    private final JwtUtil jwtUtil;
    private final AuthTokenWriteService authTokenWriteService;
    private final AuthTokenReadService authTokenReadService;
    private final OAuth2CookieAuthorizationRequestRepository OAuth2AuthorizationRequestWithCookieRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            logger.debug("Response has been committed. Unable to redirect to " + targetUrl);
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    //token을 생성하고 이를 포함한 프론트엔드로의 uri를 생성한다.
    //@SneakyThrows
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        String redirectUri = CookieUtil.getCookie(
                request, OAuth2AuthorizationRequestWithCookieRepository.REDIRECT_URI_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(appProperties.getBaseUrl() + "/signin/redirect");
        if(!isAuthorizedRedirectUri(redirectUri)) {
            throw new CustomApiException("Unauthorized Redirect URI");
        }

        String accessToken = jwtUtil.createAccessToken(authentication);

        // 리프레쉬 토큰이 존재하는지 확인
        // 리프레쉬 토큰이 존재하지 않거나 만료일까지 3일이 남지 않은 경우에만 새롭게 리프레쉬 토큰을 생성 후 저장
        AuthToken authToken = authTokenReadService.findByMemberId(((UserPrincipal) authentication.getPrincipal()).getId());
        if (authToken == null ||
                ChronoUnit.DAYS.between(LocalDateTime.now(), authToken.getRefreshTokenExpiredAt()) < 3) {
            TokenInfo refreshToken = jwtUtil.createRefreshToken();
            authToken = authTokenWriteService.saveTokenInfo(((UserPrincipal) authentication.getPrincipal()).getId(), refreshToken);

        }
        CookieUtil.addSecureCookie(response, jwtUtil.COOKIE_REFRESH_TOKEN_KEY, authToken.getRefreshToken(), (int) (jwtUtil.REFRESH_TOKEN_EXPIRE_MS/1000));

        return UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("accessToken", accessToken)
                .build()
                .toUriString();
    }

    //application.properties에 등록해놓은 Redirect uri가 맞는지 확인한다. (app.redirect-uris)
    private boolean isAuthorizedRedirectUri(String uri) {

        URI clientRedirectUri = URI.create(uri);
        return authProperties.getOauth2()
                .getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    // Only validate client host and port, especially when various client exist
                    if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }

    //인증정보를 요청한 uri 내역을 쿠키에서 삭제한다.
    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        OAuth2AuthorizationRequestWithCookieRepository.removeAuthorizationRequestCookies(request, response);
    }
}