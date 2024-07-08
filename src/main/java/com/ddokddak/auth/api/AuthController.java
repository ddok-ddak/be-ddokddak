package com.ddokddak.auth.api;

import com.ddokddak.auth.domain.dto.*;
import com.ddokddak.auth.domain.oauth.UserPrincipal;
import com.ddokddak.auth.service.EmailAuthenticationService;
import com.ddokddak.auth.service.OAuth2RefreshService;
import com.ddokddak.auth.service.OAuth2RevokeService;
import com.ddokddak.common.dto.CommonResponse;
import com.ddokddak.common.dto.TokenInfo;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.type.AuthTokenException;
import com.ddokddak.common.exception.type.MemberException;
import com.ddokddak.common.exception.type.OAuth2Exception;
import com.ddokddak.common.props.AppProperties;
import com.ddokddak.common.utils.CookieUtil;
import com.ddokddak.common.utils.JwtUtil;
import com.ddokddak.member.domain.dto.*;
import com.ddokddak.member.domain.entity.AuthToken;
import com.ddokddak.member.domain.entity.OAuth2Member;
import com.ddokddak.member.domain.enums.AuthProviderType;
import com.ddokddak.member.service.AuthTokenReadService;
import com.ddokddak.member.service.AuthTokenWriteService;
import com.ddokddak.member.service.MemberWriteService;
import com.ddokddak.member.service.OAuth2MemberReadService;
import com.ddokddak.usecase.CheckAuthUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AppProperties appProperties;
    private final MemberWriteService memberWriteService;
    private final AuthTokenWriteService authTokenWriteService;
    private final AuthTokenReadService authTokenReadService;
    private final OAuth2MemberReadService oAuth2MemberReadService;
    private final OAuth2RefreshService oAuth2RefreshService;
    private final OAuth2RevokeService oAuth2RevokeService;
    private final CheckAuthUsecase checkAuthUsecase;
    private final EmailAuthenticationService emailAuthenticationService;

    @GetMapping("/lookaround")
    public ResponseEntity<CommonResponse<SigninResponse>> lookAround(HttpServletResponse response) {
        String accessToken = jwtUtil.createAccessTokenForDev();
        SigninResponse signinResponse = SigninResponse.builder()
                .email("test@example.com")
                .accessToken(accessToken)
                .build();

        return ResponseEntity.ok()
                .body(new CommonResponse<>("Signed in for test Successfully", signinResponse));
    }

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<MemberResponse>> signUpNewUser(
            @Valid @RequestBody RegisterMemberRequest registerMemberRequest) {

        MemberResponse newMemberResponse = memberWriteService.register(registerMemberRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath() //.fromContextPath(request)
                .path("/members/me")
                .build()
                .toUri();

        return ResponseEntity.created(location)
                .body(new CommonResponse<>("User Registered Successfully", newMemberResponse));

    }

    @PostMapping(value = "/signin")
    public ResponseEntity<CommonResponse<SigninResponse>> signIn(
            @Valid @RequestBody SigningRequest signingRequest, HttpServletResponse response) throws IOException {

        var authentication = checkAuthUsecase.getAuthentication(signingRequest);

        String accessToken = jwtUtil.createAccessToken(authentication);
        SigninResponse signinResponse = SigninResponse.builder()
                .email(signingRequest.email())
                .accessToken(accessToken)
                .build();

        // 리프레쉬 토큰이 존재하는지 확인
        // 리프레쉬 토큰이 존재하지 않거나 만료일까지 3일이 남지 않은 경우에만 새롭게 리프레쉬 토큰을 생성 후 저장
        AuthToken authToken = authTokenReadService.findByMemberId(((UserPrincipal) authentication.getPrincipal()).getId());
        if (authToken == null ||
                ChronoUnit.DAYS.between(LocalDateTime.now(), authToken.getRefreshTokenExpiredAt()) < 3) {
            TokenInfo refreshToken = jwtUtil.createRefreshToken();
            authToken = authTokenWriteService.saveTokenInfo(((UserPrincipal) authentication.getPrincipal()).getId(), refreshToken);

        }
        CookieUtil.addSecureCookie(response, jwtUtil.COOKIE_REFRESH_TOKEN_KEY, authToken.getRefreshToken(), (int) (jwtUtil.REFRESH_TOKEN_EXPIRE_MS/1000));

        return ResponseEntity.ok()
                .body(new CommonResponse<>("Signed in Successfully", signinResponse));
    }

    @PostMapping(value = "/signout")
    public ResponseEntity<CommonResponse<SigninResponse>> signOut(
            HttpServletRequest request, HttpServletResponse response,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        memberWriteService.signOut(userPrincipal.getId());
        authTokenWriteService.removeAuthTokenByMemberId(userPrincipal.getId());
        CookieUtil.deleteCookie(request, response, jwtUtil.COOKIE_REFRESH_TOKEN_KEY);

        return ResponseEntity.ok()
                .body(new CommonResponse<>("Signed out Successfully", null));
    }

    @PostMapping(value = "/withdrawal/{authProviderType}")
    public ResponseEntity<CommonResponse> withdraw(
            HttpServletRequest request, HttpServletResponse response,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable AuthProviderType authProviderType) {

        if (userPrincipal.getId() == 1) {
            throw new CustomApiException(MemberException.TEST_ACCOUNT);
        }

        if (!authProviderType.equals(AuthProviderType.DEFAULT)) {
            // 서드파티 측에 연결 해제 요청 수행
            String accessToken = oAuth2RefreshService.refreshOAuth2AccessToken(userPrincipal.getId(), authProviderType);
            oAuth2RevokeService.requestRevokeUser(accessToken, authProviderType);
        }

        memberWriteService.withdraw(userPrincipal.getId());
        authTokenWriteService.removeAuthTokenByMemberId(userPrincipal.getId());
        CookieUtil.deleteCookie(request, response, jwtUtil.COOKIE_REFRESH_TOKEN_KEY);

        return ResponseEntity.ok()
                .body(new CommonResponse<>("WithDrew Successfully", null));
    }

    @GetMapping("/token/refresh")
    public ResponseEntity<CommonResponse<SigninResponse>> refreshToken (
            HttpServletRequest request, HttpServletResponse response,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        String refreshTokenValue = CookieUtil.getCookie(request, jwtUtil.COOKIE_REFRESH_TOKEN_KEY)
                .map(Cookie::getValue)
                .orElse((null));

        // 리프레쉬 토큰 유효성 체크
        if (!jwtUtil.validateToken(refreshTokenValue)) {
            throw new CustomApiException(AuthTokenException.INVALID_REFRESH_TOKEN);
        }
        // 리프레쉬 토큰이 존재하는지 확인
        // 리프레쉬 토큰이 존재하지 않거나 값이 다른 경우 예외처리
        AuthToken authToken = authTokenReadService.findByMemberId(userPrincipal.getId());
        if (authToken == null || authToken.getRefreshToken() != refreshTokenValue) {
            throw new CustomApiException(AuthTokenException.INVALID_REFRESH_TOKEN);
        }
        // 소셜 로그인 회원의 경우
        // 소셜 제공 리프레쉬 토큰이 유효한지 체크
        OAuth2Member oAuth2Member = oAuth2MemberReadService.findByMemberId(userPrincipal.getId());
        if (oAuth2Member != null &&
                (oAuth2Member.getRefreshTokenExpiredAt() != null && oAuth2Member.getRefreshTokenExpiredAt().isAfter(LocalDateTime.now()))) {
            throw new CustomApiException(OAuth2Exception.EXPIRED_SOCIAL_REFRESH_TOKEN);
        }

        String accessToken = jwtUtil.createAccessToken(userPrincipal);
        SigninResponse signinResponse = SigninResponse.builder()
                .email(userPrincipal.getName())
                .accessToken(accessToken)
                .build();

        // 만료일까지 3일이 남지 않은 경우에만 새롭게 리프레쉬 토큰을 생성 후 저장
        if (ChronoUnit.DAYS.between(LocalDateTime.now(), authToken.getRefreshTokenExpiredAt()) < 3) {
            TokenInfo refreshToken = jwtUtil.createRefreshToken();
            authToken = authTokenWriteService.saveTokenInfo(userPrincipal.getId(), refreshToken);
        }
        CookieUtil.addSecureCookie(response, jwtUtil.COOKIE_REFRESH_TOKEN_KEY, authToken.getRefreshToken(), (int) (jwtUtil.REFRESH_TOKEN_EXPIRE_MS/1000));

        return ResponseEntity.ok()
                .body(new CommonResponse<>("Refresh Auth Token Successfully", signinResponse));
    }

    @PostMapping("/email/code")
    public ResponseEntity<CommonResponse<AuthenticationNumberResponse>> requestAuthenticationNumber(
            @Valid @RequestBody AuthenticationNumberRequest request
    ){
        var createdId = emailAuthenticationService.mailSendingProcess(request);
        return ResponseEntity.ok(new CommonResponse<>("SUCCESS", new AuthenticationNumberResponse(createdId)));
    }

    @PostMapping("/email/verification")
    public ResponseEntity<CommonResponse<Boolean>> checkAuthenticationNumber(
            @Valid @RequestBody CheckEmailAuthenticationRequest request
    ){
        return ResponseEntity.ok(
                new CommonResponse<>(
                        "SUCCESS", emailAuthenticationService.checkAuthenticationNumber(request)
                )
        );
    }
}
