package com.ddokddak.auth.api;

import com.ddokddak.auth.service.EmailAuthenticationService;
import com.ddokddak.common.dto.CommonResponse;
import com.ddokddak.common.props.AppProperties;
import com.ddokddak.common.utils.CookieUtil;
import com.ddokddak.common.utils.JwtUtil;
import com.ddokddak.member.dto.*;
import com.ddokddak.member.service.MemberWriteService;
import com.ddokddak.usecase.CheckAuthUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AppProperties appProperties;
    private final MemberWriteService memberWriteService;
    private final CheckAuthUsecase checkAuthUsecase;
    private final EmailAuthenticationService emailAuthenticationService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponse<?>> signUpNewUser(@Valid @RequestBody RegisterMemberRequest registerMemberRequest) {

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
            @Valid @RequestBody SigningRequest signingRequest, HttpServletResponse response) {

        var authentication = checkAuthUsecase.getAuthentication(signingRequest);
        String accessToken = jwtUtil.createAccessToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(appProperties.getBaseUrl() + "/signin/redirect"));
        httpHeaders.add(JwtUtil.AUTHORIZATION_HEADER, "Bearer " + accessToken);
        CookieUtil.addCookie(response, CookieUtil.ACCESS_TOKEN_COOKIE_NAME, accessToken, CookieUtil.COOKIE_EXPIRE_SECONDS);

//        SigninResponse signinResponse = SigninResponse.builder()
//                .email(signingRequest.email())
//                .authorization("Bearer " + accessToken)
//                .build();

        return new ResponseEntity<>(new CommonResponse<>("Signed in Successfully", null),
                httpHeaders,
                HttpStatus.MOVED_PERMANENTLY);
//                .headers(httpHeaders)
//                .body(new CommonResponse<>(, signinResponse));
    }

    @PostMapping("/email/requestAuthenticationNumber")
    public ResponseEntity<CommonResponse<Boolean>> requestAuthenticationNumber(
            @Valid @RequestBody AuthenticationNumberRequest request
    ){
        emailAuthenticationService.mailSendingProcess(request);
        return ResponseEntity.ok(new CommonResponse<>("SUCCESS", Boolean.TRUE));
    }

    @PostMapping("/email/checkAuthenticationNumber")
    public ResponseEntity<CommonResponse<Boolean>> checkAuthenticationNumber(
            @Valid @RequestBody CheckAuthenticationNumberRequest request
    ){
        return ResponseEntity.ok(
                new CommonResponse<>(
                        "SUCCESS", emailAuthenticationService.checkAuthenticationNumber(request)
                )
        );
    }

    @PostMapping(value = "/signout")
    public ResponseEntity<CommonResponse<SigninResponse>> signOut(HttpServletRequest request, HttpServletResponse response) {

        CookieUtil.deleteCookie(request, response, CookieUtil.ACCESS_TOKEN_COOKIE_NAME);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(appProperties.getBaseUrl()));

        return new ResponseEntity<>(new CommonResponse<>("Signed out Successfully", null),
                httpHeaders,
                HttpStatus.MOVED_PERMANENTLY);
    }
}
