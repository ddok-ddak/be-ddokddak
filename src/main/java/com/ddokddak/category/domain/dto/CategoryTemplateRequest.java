package com.ddokddak.category.domain.dto;

import com.ddokddak.member.domain.enums.TemplateType;
import lombok.Builder;
import javax.validation.constraints.NotNull;

public record CategoryTemplateRequest(
        @NotNull TemplateType templateType
) {
    @Builder
    public CategoryTemplateRequest(TemplateType templateType) {
        this.templateType = templateType;
    }
    public CategoryTemplateRequest(String name) {
        this(TemplateType.valueOf(name));
    }
}
