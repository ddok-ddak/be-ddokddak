package com.ddokddak.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TokenInfo {
    private String token;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;
}
