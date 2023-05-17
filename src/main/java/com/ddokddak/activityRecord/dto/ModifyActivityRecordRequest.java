package com.ddokddak.activityRecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.Builder;

import java.time.ZonedDateTime;

public record ModifyActivityRecordRequest(
        @NotNull Long id,
        @NotNull Long categoryId,
        String content,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss z") ZonedDateTime startedAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss z") ZonedDateTime finishedAt
    ){

    @Builder
    public ModifyActivityRecordRequest{}
}
