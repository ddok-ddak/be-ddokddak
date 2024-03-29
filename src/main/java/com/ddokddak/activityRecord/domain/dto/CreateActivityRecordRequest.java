package com.ddokddak.activityRecord.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.springframework.lang.Nullable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
