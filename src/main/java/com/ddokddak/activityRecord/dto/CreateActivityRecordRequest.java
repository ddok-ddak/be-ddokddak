package com.ddokddak.activityRecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record CreateActivityRecordRequest(
        @NotNull Long categoryId,
        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startedAt,
        @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime finishedAt,
        @Nullable String content,
        @NotNull Integer timeUnit // default 30 (10/30/60)
) {
    @Builder
    public CreateActivityRecordRequest {}
}
