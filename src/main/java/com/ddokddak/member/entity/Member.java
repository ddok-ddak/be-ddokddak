package com.ddokddak.member.entity;

import com.ddokddak.common.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 유니크... 같은 메일이면서 다른 소셜 로그인 활용하는 경우는 없도록
    @Column(unique = true, length = 100)
    private String email;

    @Column(unique = true)
    private String oauthId;

    @Column(length = 100)
    private String password;

    @Column(unique = true, length = 100) //nullable = false,
    private String nickname;

    private String name;

    @Enumerated(EnumType.STRING)
    private TemplateType templateType;

    @Enumerated(EnumType.STRING)
    @NotNull // @NotEmpty 사용 불가
    private RoleType role;

    @Enumerated(EnumType.STRING)
    private AuthProviderType authProviderType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    private String imageUrl;

    private Short failedPasswordCount;

    @PrePersist // 사용자 상태 - 수정 필요
    public void prePersist() {
        this.failedPasswordCount = 0;
        this.status = this.status == null ? Status.NORMAL : this.status;
    }

    public Member updateProfile(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
        return this;
    }

    public void plusFailedPasswordTryCount() {
        if(this.failedPasswordCount == 5 && this.status == Status.NORMAL) {
            this.status = Status.PASSWORD_FAILED;
            return;
        }
        this.failedPasswordCount++;
    }

    public String getRoleCode() {
        return this.role.getCode();
    }
}