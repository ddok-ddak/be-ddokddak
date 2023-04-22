package com.ddokddak.category.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseTemplate implements CategoryTemplate {

    GROWTH(null, "성장",  "#FFCDA0"),
    READING("성장", "독서", "#FFCDA0"),
    LECTURE("성장", "강의", "#FFCDA0"),
    CERTIFICATION("성장", "자격증", "#FFCDA0"),

    RELATIONSHIP(null, "관계",  "#FFE49F"),
    FRIENDS("관계", "친구", "#FFE49F"),
    FAMILY("관계", "가족", "#FFE49F"),
    LOVER("관계", "연인", "#FFE49F"),

    HEALTH(null, "건강",  "#B5F9AA"),
    SLEEP("건강", "잠", "#B5F9AA"),
    MEAL("건강", "식사", "#B5F9AA"),
    WORKOUT("건강", "운동", "#B5F9AA"),

    WASTE(null, "낭비",  "#B9E2FF"),
    SNS("낭비", "sns", "#B9E2FF"),
    WEB_SURFING("낭비", "웹서핑", "#B9E2FF"),
    MEDIA("낭비", "미디어", "#B9E2FF"),
    DAY_DREAMING("낭비", "멍", "#B9E2FF"),

    HOBBY(null, "취미",  "#D2C7FF"),
    GAME("취미", "게임", "#D2C7FF"),
    MOVIE("취미", "영화", "#D2C7FF"),
    MUSIC("취미", "음악", "#D2C7FF"),
    INSTRUMENT("취미", "악기", "#D2C7FF"),

    ETC(null, "기타",  "#ECB8FF"),
    HOUSE_CHORE("기타", "집안일", "#ECB8FF"),
    TRAVEL_TIME("기타", "이동시간", "#ECB8FF");

    private final String parentName;
    private final String name;
    private final String color;

    @Getter
    @RequiredArgsConstructor
    public enum Worker implements CategoryTemplate {
        JOB(null, "직장",  "#FFC5CC"),
        WORK("직장", "업무", "#FFC5CC"),
        OVERTIME_WORK("직장", "야근", "#FFC5CC"),
        BUSINESS_TRIP("직장", "출장", "#FFC5CC"),
        COMPANY_DINNER("직장", "회식", "#FFC5CC");

        private final String parentName;
        private final String name;
        private final String color;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Student implements CategoryTemplate {
        STUDY(null, "학업",  "#FFC5CC"),
        LECTURE("학업", "강의", "#FFC5CC"),
        ASSIGNMENT("학업", "과제", "#FFC5CC"),
        STUDY_GROUP("학업", "스터디", "#FFC5CC"),
        GROUP_WORK("학업", "팀플", "#FFC5CC"),
        STUDY_FOR_EXAM("학업", "시험공부", "#FFC5CC");

        private final String parentName;
        private final String name;
        private final String color;
    }
}
