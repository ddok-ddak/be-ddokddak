package com.ddokddak.category.dto;

import com.ddokddak.member.entity.TemplateType;
import lombok.Builder;
import javax.validation.constraints.NotNull;

public record AddCategoryTemplateRequest(
        @NotNull TemplateType templateType
) {
    @Builder
    public AddCategoryTemplateRequest(TemplateType templateType) {
        this.templateType = templateType;
    }
    public AddCategoryTemplateRequest(String name) {
        this(TemplateType.valueOf(name));
    }
}
