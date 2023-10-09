package com.ddokddak.member.entity.enums;

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

    NONE("NONE", "미등록", null),
    UNEMPLOYED("UNEMPLOYED","일반인", new CategoryTemplate[0]),
    WORKER("WORKER","회사원", BaseTemplate.Worker.values()),
    STUDENT("STUDENT","학생", BaseTemplate.Student.values());

    private final String code;
    private final String displayName;

    private final CategoryTemplate[] specificTemplates;
    public final List<CategoryTemplate> getTemplates() {
        return Stream.of(BaseTemplate.values(), this.specificTemplates)
                .flatMap(Stream::of)
                .toList();
    }
}
