package com.ddokddak.activityRecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import java.time.LocalDateTime;

public record ReadActivityRecordRequest(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
        LocalDateTime fromStartedAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
        LocalDateTime toStartedAt
) {
    @Builder
    public ReadActivityRecordRequest {}
}
