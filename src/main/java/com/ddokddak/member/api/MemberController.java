package com.ddokddak.member.api;

import com.ddokddak.auth.domain.UserPrincipal;
import com.ddokddak.common.dto.CommonResponse;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.utils.JwtUtil;
import com.ddokddak.member.dto.*;
import com.ddokddak.member.service.MemberReadService;
import com.ddokddak.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberReadService memberReadService;

    @GetMapping("/duplicatedEmail")
    public ResponseEntity<CommonResponse<?>> checkIfDuplicatedEmail(@Valid ValidateMemberRequest dto) {

        var email = dto.email();
        memberReadService.checkIfDuplicatedUserByEmail(email);
        return ResponseEntity.ok()
                .body(new CommonResponse<>("Able to use", email));
    }

    @GetMapping("/duplicateNickname")
    public ResponseEntity<CommonResponse<?>> checkIfDuplicatedNickname(@Valid ValidateMemberRequest dto) {

        var nickname = dto.nickname();
        memberReadService.checkIfDuplicatedUserByNickname(nickname);
        return ResponseEntity.ok()
                .body(new CommonResponse<>("Able to use", nickname));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public CommonResponse<MemberResponse> getCurrentUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        MemberResponse memberResponse = memberReadService.getUserDtoByEmail(userPrincipal.getName());
        return new CommonResponse<>("User Info getting by UserEmail", memberResponse);
    }
}
