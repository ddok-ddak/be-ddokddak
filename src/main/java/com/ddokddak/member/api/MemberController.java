package com.ddokddak.member.api;

import com.ddokddak.auth.domain.oauth.UserPrincipal;
import com.ddokddak.category.domain.dto.CategoryTemplateRequest;
import com.ddokddak.common.dto.CommonResponse;
import com.ddokddak.member.domain.dto.MemberResponse;
import com.ddokddak.member.domain.dto.ModifyStartDayRequest;
import com.ddokddak.member.domain.dto.ModifyStartTimeRequest;
import com.ddokddak.member.service.MemberReadService;
import com.ddokddak.member.service.MemberWriteService;
import com.ddokddak.usecase.CreateCategoryTemplateUsecase;
import com.ddokddak.usecase.ModifyCategoryTemplateUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberReadService memberReadService;
    private final MemberWriteService memberWriteService;
    private final CreateCategoryTemplateUsecase createCategoryTemplateUsecase;
    private final ModifyCategoryTemplateUsecase modifyCategoryTemplateUsecase;


    @GetMapping("/duplicatedEmail")
    public ResponseEntity<CommonResponse<String>> checkIfDuplicatedEmail(
            @RequestParam @Valid @Email @Size(min = 5, max = 100) String email) {

        memberReadService.checkIfDuplicatedUserByEmail(email);
        return ResponseEntity.ok()
                .body(new CommonResponse<>("Able to use", email));
    }

    @GetMapping("/duplicateNickname")
    public ResponseEntity<CommonResponse<String>> checkIfDuplicatedNickname(
            @RequestParam @Valid @Size(min = 1, max = 100) String nickname) {

        memberReadService.checkIfDuplicatedUserByNickname(nickname);
        return ResponseEntity.ok()
                .body(new CommonResponse<>("Able to use", nickname));
    }
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/custom/start-time")
    public ResponseEntity<CommonResponse<Boolean>> modifyStartTime(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody ModifyStartTimeRequest req) {
        memberWriteService.updateStartTime(userPrincipal.getId(), req);
        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/custom/start-day")
    public ResponseEntity<CommonResponse<Boolean>> modifyStartDay(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody ModifyStartDayRequest req) {
        memberWriteService.updateStartDay(userPrincipal.getId(), req);
        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/custom/category-template")
    public ResponseEntity<CommonResponse<Boolean>> setCategoryTemplateTypeForTheFirstTime(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody CategoryTemplateRequest req) {

        createCategoryTemplateUsecase.execute(req, userPrincipal.getId());
        return ResponseEntity.ok(new CommonResponse<>("Successfully Set Category Template", Boolean.TRUE));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/custom/category-template")
    public ResponseEntity<CommonResponse<Boolean>> modifyCategoryTemplateType(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody CategoryTemplateRequest req) {

        modifyCategoryTemplateUsecase.execute(req, userPrincipal.getId());
        return ResponseEntity.ok(new CommonResponse<>("Successfully Updated", Boolean.TRUE));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/me")
    public ResponseEntity<CommonResponse<MemberResponse>> getCurrentUserInfo(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        var memberResponse = memberReadService.getUserDtoByEmail(userPrincipal.getName());
        return ResponseEntity.ok(new CommonResponse<>("User Info getting by UserEmail", memberResponse));
    }
}
