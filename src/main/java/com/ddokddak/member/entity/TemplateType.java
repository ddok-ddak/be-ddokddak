package com.ddokddak.member.entity;

import com.ddokddak.category.enums.BaseTemplate;
import com.ddokddak.category.enums.CategoryTemplate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum TemplateType {
    NONE("미등록", new CategoryTemplate[0]),
    UNEMPLOYED("무직", new CategoryTemplate[0]),
    WORKER("회사원", BaseTemplate.Worker.values()),
    STUDENT("학생", BaseTemplate.Student.values());

    private final String displayName;
    private final CategoryTemplate[] specificTemplates;
    public final List<CategoryTemplate> getTemplates() {
        return Stream.of(BaseTemplate.values(), this.specificTemplates)
                .flatMap(Stream::of)
                .toList();
    }
}
