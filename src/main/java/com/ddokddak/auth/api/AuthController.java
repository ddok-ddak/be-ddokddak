package com.ddokddak.auth.api;

import com.ddokddak.common.dto.CommonResponse;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.utils.CookieUtil;
import com.ddokddak.common.utils.JwtUtil;
import com.ddokddak.member.dto.MemberResponse;
import com.ddokddak.member.dto.RegisterMemberRequest;
import com.ddokddak.member.dto.SigninResponse;
import com.ddokddak.member.dto.SigningRequest;
import com.ddokddak.member.service.MemberWriteService;
import com.ddokddak.usecase.CheckAuthUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final MemberWriteService memberWriteService;
    private final CheckAuthUsecase checkAuthUsecase;

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
        httpHeaders.setLocation(URI.create("http://localhost:3000/signin/redirect"));
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
}
