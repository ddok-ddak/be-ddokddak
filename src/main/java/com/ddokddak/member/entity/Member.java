package com.ddokddak.member.entity;

import com.ddokddak.common.entity.BaseTimeEntity;
import com.ddokddak.common.exception.CustomApiException;
import com.ddokddak.common.exception.type.BaseException;
import com.ddokddak.common.exception.type.CategoryException;
import com.ddokddak.member.entity.enums.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @Column(length = 100)
    private String password;

    @Column(unique = true, length = 100) //nullable = false,
    private String nickname;

    private String name;

    private String imageUrl;

    @NotNull
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'")
    @Builder.Default
    private RoleType role = RoleType.USER;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NORMAL'")
    @Builder.Default
    private Status status = Status.NORMAL;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    @Builder.Default
    private TemplateType templateType = TemplateType.NONE;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'SUNDAY'")
    @Builder.Default
    private CustomOpt.StartDay startDay = CustomOpt.StartDay.SUNDAY;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'AM4'")
    @Builder.Default
    private CustomOpt.StartTime startTime = CustomOpt.StartTime.AM4;

    @ColumnDefault("0")
    @Builder.Default
    private Short failedPasswordCount = 0;

    @Column(length = 256)
    private String accessToken;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'DEFAULT'")
    @Builder.Default
    private AuthProviderType authProvider = AuthProviderType.DEFAULT;

    @Column(name = "oauth2_id", unique = true)
    private String oauth2Id;

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

    public void registerTemplateType(TemplateType templateType) {
        this.templateType = templateType;
    }

    public void modifyStartTime(CustomOpt.StartTime startTime) {
        this.startTime = startTime;
    }

    public void modifyStartDay(CustomOpt.StartDay startDay) {
        this.startDay = startDay;
    }

    public void setCategoryTemplateType(TemplateType templateType) {
        if (!this.templateType.equals(TemplateType.NONE)) {
            throw new CustomApiException(CategoryException.ALREADY_EXISTS); // 최초 등록한 경우가 아니면 비즈니스 로직 예외 처리
        }
        this.templateType = templateType;
    }

    public TemplateType modifyCategoryTemplateType(TemplateType templateType) {

        if (this.templateType.equals(TemplateType.NONE)) {
            throw new CustomApiException(BaseException.UNABLE_REQUEST); // 최초 등록 전이면 비즈니스 로직 예외 처리
        }

        if (this.templateType.equals(templateType)) {
            throw new CustomApiException(CategoryException.ALREADY_EXISTS); // 동일한 템플릿이면 비즈니스 로직 예외 처리
        }

        var previousTemplateType = this.templateType;
        this.templateType = templateType;
        return previousTemplateType;
    }
}