package com.ddokddak.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class CustomOpt {
    @Getter
    @RequiredArgsConstructor
    public enum StartDay {
        SUNDAY("SUNDAY", "일요일"),
        MONDAY("MONDAY", "월요일");

        private final String code;
        private final String displayName;
    }

    @Getter
    @RequiredArgsConstructor
    public enum StartTime {
        _4AM("4AM", "4AM"),
        _12AM("12AM", "4AM");

        private final String code;
        private final String displayName;
    }
}
