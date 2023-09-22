package com.ddokddak.member.service;

import com.ddokddak.auth.domain.oauth.OAuth2UserInfo;
import com.ddokddak.category.dto.CategoryTemplateRequest;
import com.ddokddak.category.service.CategoryWriteService;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.NotValidRequestException;
import com.ddokddak.common.exception.type.MemberException;
import com.ddokddak.member.dto.MemberResponse;
import com.ddokddak.member.dto.ModifyStartDayRequest;
import com.ddokddak.member.dto.ModifyStartTimeRequest;
import com.ddokddak.member.dto.RegisterMemberRequest;
import com.ddokddak.member.entity.*;
import com.ddokddak.member.mapper.MemberMapper;
import com.ddokddak.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class MemberWriteService {
    private final MemberRepository memberRepository;
    private final CategoryWriteService categoryWriteService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponse register(RegisterMemberRequest registerMemberRequest) throws CustomApiException {

        if (memberRepository.existsByEmail(registerMemberRequest.email())) {
            throw new CustomApiException(MemberException.ALREADY_EXISTS_EMAIL);
        }
        if (memberRepository.existsByNickname(registerMemberRequest.nickname())) {
            throw new CustomApiException(MemberException.ALREADY_EXISTS_NAME);
        }

        var member = MemberMapper.fromRegisterMemberRequest(registerMemberRequest, this.passwordEncoder);
        memberRepository.save(member);

        return MemberMapper.toMemberResponse(member);
    }

    @Transactional
    public Member registerForOauth2(OAuth2UserInfo oAuth2UserInfo) throws CustomApiException {

        StringBuffer name = new StringBuffer(oAuth2UserInfo.getName());
        String tempName = name.toString();

        // TODO unique 이름 생성하기 고민, 유사한 이름 전부 조회해서 피하기?ㅠ, 트랜잭션 고민, 리트라이?
        while (memberRepository.existsByNickname(tempName)) {
            tempName = name.append('-')
                    .append(UUID.randomUUID()) // 좀 더 짧게 랜덤화
                    .toString();
        }

        Member member = oAuth2UserInfo.toEntity(tempName);
        memberRepository.save(member);
        return member;
    }

    @Transactional
    public void countFailedPasswordTry(String email) {
        var member = memberRepository.findByEmail(email)
                .orElseThrow(()->new NotValidRequestException(MemberException.MEMBER_ID));
        member.plusFailedPasswordTryCount();
    }

    @Transactional
    public void updateStartTime(Long memberId, ModifyStartTimeRequest req) {
        var member = memberRepository.findById(memberId)
                .orElseThrow(()->new NotValidRequestException(MemberException.MEMBER_ID));
        member.modifyStartTime(req.startTime());
    }

    @Transactional
    public void updateStartDay(Long memberId, ModifyStartDayRequest req) {
        var member = memberRepository.findById(memberId)
                .orElseThrow(()->new NotValidRequestException(MemberException.MEMBER_ID));
        member.modifyStartDay(req.startDay());
    }

    @Transactional
    public void setCategoryTemplate(Long memberId, CategoryTemplateRequest req) {
        var member = memberRepository.findById(memberId)
                .orElseThrow(()->new NotValidRequestException(MemberException.MEMBER_ID));
        member.setCategoryTemplateType(req.templateType());
    }

    @Transactional
    public TemplateType modifyCategoryTemplate(Long memberId, CategoryTemplateRequest req) {
        var member = memberRepository.findById(memberId)
                .orElseThrow(()->new NotValidRequestException(MemberException.MEMBER_ID));
        var previousTemplateType = member.modifyCategoryTemplateType(req.templateType());
        return previousTemplateType;
    }
}
