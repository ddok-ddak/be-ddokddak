package com.ddokddak.member.mapper;

import com.ddokddak.member.domain.dto.MemberResponse;
import com.ddokddak.auth.domain.dto.RegisterMemberRequest;
import com.ddokddak.member.domain.entity.Member;
import com.ddokddak.member.domain.enums.AuthProviderType;
import com.ddokddak.member.domain.enums.RoleType;
import com.ddokddak.member.domain.enums.Status;
import com.ddokddak.member.domain.enums.TemplateType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class MemberMapper {

    public static final Member fromRegisterMemberRequest(RegisterMemberRequest request, PasswordEncoder passwordEncoder) {

        return Member.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .nickname(request.nickname()!=null?request.nickname():request.email())
                .role(RoleType.USER)
                .authProvider(AuthProviderType.DEFAULT)
                .status(Status.NORMAL)
                .templateType(TemplateType.NONE)
                .build();
    }

    public static final MemberResponse toMemberResponse(Member member) {
        return MemberResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .role(member.getRole())
                .authProviderType(member.getAuthProvider())
                .status(member.getStatus())
                .templateType(member.getTemplateType())
                .startDay(member.getStartDay())
                .startTime(member.getStartTime())
                .build();
    }
}
