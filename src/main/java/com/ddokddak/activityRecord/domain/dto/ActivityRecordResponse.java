package com.ddokddak.activityRecord.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record ActivityRecordResponse(
        Long activityRecordId,
        Long categoryId,
        String categoryName,
        String categoryColor,
        String content,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startedAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime finishedAt,
        Integer timeUnit
) {
    @Builder
    public ActivityRecordResponse {}
}
