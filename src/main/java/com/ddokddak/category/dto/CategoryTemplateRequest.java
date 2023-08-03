package com.ddokddak.category.dto;

import com.ddokddak.member.entity.TemplateType;
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
