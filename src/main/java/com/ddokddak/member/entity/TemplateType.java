package com.ddokddak.member.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TemplateType {

    WORKER("회사원"),
    STUDENT("학생");

    private final String displayName;
}