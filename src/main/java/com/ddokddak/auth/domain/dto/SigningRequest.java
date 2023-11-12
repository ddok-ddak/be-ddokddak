package com.ddokddak.auth.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public record SigningRequest(
        @NotNull @Size(min = 3, max = 100) String email,
        @NotNull @Size(min = 3, max = 100) String password)
{
    @Builder
    public SigningRequest {}
}
