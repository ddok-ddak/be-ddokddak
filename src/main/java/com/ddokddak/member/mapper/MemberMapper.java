package com.ddokddak.member.mapper;

import com.ddokddak.member.dto.MemberResponse;
import com.ddokddak.member.dto.RegisterMemberRequest;
import com.ddokddak.member.entity.AuthProviderType;
import com.ddokddak.member.entity.Member;
import com.ddokddak.member.entity.RoleType;
import com.ddokddak.member.entity.Status;
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
                .authProviderType(AuthProviderType.DEFAULT)
                .status(Status.NORMAL)
                .build();
    }

    public static final MemberResponse toMemberResponse(Member member) {
        return MemberResponse.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .role(member.getRole())
                .authProviderType(member.getAuthProviderType())
                .status(member.getStatus())
                .build();
    }
}
