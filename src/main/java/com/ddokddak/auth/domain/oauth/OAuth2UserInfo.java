package com.ddokddak.auth.domain.oauth;

import com.ddokddak.member.entity.*;
import com.ddokddak.member.entity.enums.AuthProviderType;
import com.ddokddak.member.entity.enums.RoleType;
import com.ddokddak.member.entity.enums.Status;
import com.ddokddak.member.entity.enums.TemplateType;

import java.util.Map;

public abstract class OAuth2UserInfo {
  private Map<String, Object> attributes;

  public OAuth2UserInfo(Map<String, Object> attributes) {
      this.attributes = attributes;
  }

  public Map<String, Object> getAttributes() {
      return attributes;
  }

  public Member toEntity(String tempName) {

    return Member.builder()
            .name(tempName) // 유니크 속성을 위해 선검증 후, 중복이 있다면 난수를 붙인 값
            .nickname(getName())
            .email(getEmail())
            .imageUrl(getImageUrl())
            .authProvider(getProviderType())
            .role(RoleType.USER)
            .status(Status.NORMAL)
            .templateType(TemplateType.NONE)
            .build();
  }

  public abstract AuthProviderType getProviderType();

  public abstract String getId();

  public abstract String getName();

  public abstract String getEmail();

  public abstract String getImageUrl();

}