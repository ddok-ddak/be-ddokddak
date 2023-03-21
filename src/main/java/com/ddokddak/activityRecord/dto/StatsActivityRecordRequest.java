package com.ddokddak.activityRecord.dto;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record StatsActivityRecordRequest(
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime fromStartedAt,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime toFinishedAt) {
    @Builder
    public StatsActivityRecordRequest {}
}
