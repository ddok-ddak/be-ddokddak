package com.ddokddak.auth.domain.oauth;

import com.ddokddak.member.entity.AuthProviderType;
import com.ddokddak.member.entity.Member;
import com.ddokddak.member.entity.RoleType;
import com.ddokddak.member.entity.Status;
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
            .role(RoleType.USER)
            .status(Status.NORMAL)
            .authProviderType(getProviderType())
            .build();
  }

  public abstract AuthProviderType getProviderType();

  public abstract String getId();

  public abstract String getName();

  public abstract String getEmail();

  public abstract String getImageUrl();

}