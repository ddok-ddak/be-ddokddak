package com.ddokddak.activityRecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import java.time.ZonedDateTime;

public record ActivityRecordResponse(
        Long activityRecordId,
        Long categoryId,
        String categoryName,
        String categoryColor,
        String content,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss z") ZonedDateTime startedAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss z") ZonedDateTime finishedAt,
        String timeZone,
        Integer timeUnit
) {
    @Builder
    public ActivityRecordResponse {}
}
