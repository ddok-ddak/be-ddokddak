package com.ddokddak.activityRecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

public record ReadActivityRecordResponse(
        Long activityRecordId,
        Long categoryId,
        String categoryName,
        String categoryColor,
        String content,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") Timestamp startedAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") Timestamp finishedAt,
        String timeZone,
        Integer timeUnit
) {
    @Builder
    public ReadActivityRecordResponse {}
}
