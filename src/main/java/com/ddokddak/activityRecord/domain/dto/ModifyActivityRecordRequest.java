package com.ddokddak.activityRecord.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record ModifyActivityRecordRequest(
        @NotNull Long id,
        @NotNull Long categoryId,
        String content,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startedAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime finishedAt
    ){

    @Builder
    public ModifyActivityRecordRequest{}
}
