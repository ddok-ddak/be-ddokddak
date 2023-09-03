package com.ddokddak.category.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseTemplate implements CategoryTemplate {

    GROWTH(null, "성장",  "#FFCDA0", "default.jpg"),
    READING("성장", "독서", "#FFCDA0", "default.jpg"),
    LECTURE("성장", "강의", "#FFCDA0", "default.jpg"),
    CERTIFICATION("성장", "자격증", "#FFCDA0", "default.jpg"),

    RELATIONSHIP(null, "관계",  "#FFE49F", "default.jpg"),
    FRIENDS("관계", "친구", "#FFE49F", "default.jpg"),
    FAMILY("관계", "가족", "#FFE49F", "default.jpg"),
    LOVER("관계", "연인", "#FFE49F", "default.jpg"),

    HEALTH(null, "건강",  "#B5F9AA", "default.jpg"),
    SLEEP("건강", "잠", "#B5F9AA", "default.jpg"),
    MEAL("건강", "식사", "#B5F9AA", "default.jpg"),
    WORKOUT("건강", "운동", "#B5F9AA", "default.jpg"),

    WASTE(null, "낭비",  "#B9E2FF", "default.jpg"),
    SNS("낭비", "sns", "#B9E2FF", "default.jpg"),
    WEB_SURFING("낭비", "웹서핑", "#B9E2FF", "default.jpg"),
    MEDIA("낭비", "미디어", "#B9E2FF", "default.jpg"),
    DAY_DREAMING("낭비", "멍", "#B9E2FF", "default.jpg"),

    HOBBY(null, "취미",  "#D2C7FF", "default.jpg"),
    GAME("취미", "게임", "#D2C7FF", "default.jpg"),
    MOVIE("취미", "영화", "#D2C7FF", "default.jpg"),
    MUSIC("취미", "음악", "#D2C7FF", "default.jpg"),
    INSTRUMENT("취미", "악기", "#D2C7FF", "default.jpg"),

    ETC(null, "기타",  "#ECB8FF", "default.jpg"),
    HOUSE_CHORE("기타", "집안일", "#ECB8FF", "default.jpg"),
    TRAVEL_TIME("기타", "이동시간", "#ECB8FF", "default.jpg");

    private final String parentName;
    private final String name;
    private final String color;
    private final String iconName;

    @Getter
    @RequiredArgsConstructor
    public enum Worker implements CategoryTemplate {
        JOB(null, "직장",  "#FFC5CC", "default.jpg"),
        WORK("직장", "업무", "#FFC5CC", "default.jpg"),
        OVERTIME_WORK("직장", "야근", "#FFC5CC", "default.jpg"),
        BUSINESS_TRIP("직장", "출장", "#FFC5CC", "default.jpg"),
        COMPANY_DINNER("직장", "회식", "#FFC5CC", "default.jpg");

        private final String parentName;
        private final String name;
        private final String color;
        private final String iconName;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Student implements CategoryTemplate {
        STUDY(null, "학업",  "#FFC5CC", "default.jpg"),
        LECTURE("학업", "강의", "#FFC5CC", "default.jpg"),
        ASSIGNMENT("학업", "과제", "#FFC5CC", "default.jpg"),
        STUDY_GROUP("학업", "스터디", "#FFC5CC", "default.jpg"),
        GROUP_WORK("학업", "팀플", "#FFC5CC", "default.jpg"),
        STUDY_FOR_EXAM("학업", "시험공부", "#FFC5CC", "default.jpg");

        private final String parentName;
        private final String name;
        private final String color;
        private final String iconName;
    }
}
