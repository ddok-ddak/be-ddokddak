package com.ddokddak.member.domain.entity;

import com.ddokddak.common.dto.TokenInfo;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AuthToken {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEMBER_ID", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private Long memberId;

    @Column(name = "REFRESH_TOKEN", length = 256)
    @NotNull
    @Size(max = 256)
    private String refreshToken;

    @Column
    private LocalDateTime refreshTokenIssuedAt;

    @Column
    private LocalDateTime refreshTokenExpiredAt;

    public void modifyRefreshToken(TokenInfo refreshToken) {
        this.refreshToken = refreshToken.getToken();
        this.refreshTokenIssuedAt = refreshToken.getIssuedAt();
        this.refreshTokenExpiredAt = refreshToken.getExpiredAt();
    }
}
