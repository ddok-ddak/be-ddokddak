package com.ddokddak.category.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseTemplate implements CategoryTemplate {

    GROWTH(null, "성장",  "#FFCDA0", "#FFA24F", "base", "growth.svg"),
    READING("성장", "독서", "#FFCDA0", "#FFA24F", "base", "reading.svg"),
    LECTURE("성장", "강의", "#FFCDA0", "#FFA24F", "base", "lecture.svg"),
    CERTIFICATION("성장", "자격증", "#FFCDA0", "#FFA24F", "base", "certificate.svg"),

    RELATIONSHIP(null, "관계",  "#FFE49F", "#FFC32B", "base", "relationship.svg"),
    FRIENDS("관계", "친구", "#FFE49F", "#FFC32B", "base", "friend.svg"),
    FAMILY("관계", "가족", "#FFE49F", "#FFC32B", "base", "family.svg"),
    LOVER("관계", "연인", "#FFE49F", "#FFC32B", "base", "dating.svg"),

    HEALTH(null, "건강",  "#C8F9AA", "#3EE423", "base", "health.svg"),
    SLEEP("건강", "잠", "#C8F9AA", "#3EE423", "base", "sleep.svg"),
    MEAL("건강", "식사", "#C8F9AA", "#3EE423", "base", "meal.svg"),
    WORKOUT("건강", "운동", "#C8F9AA", "#3EE423", "base", "exercise.svg"),

    WASTE(null, "낭비",  "#B9E2FF", "#54B8FF", "base", "wasting.svg"),
    SNS("낭비", "sns", "#B9E2FF", "#54B8FF", "base", "sns.svg"),
    WEB_SURFING("낭비", "웹서핑", "#B9E2FF", "#54B8FF", "base", "internet.svg"),
    MEDIA("낭비", "미디어", "#B9E2FF", "#54B8FF", "base", "media.svg"),
    DAY_DREAMING("낭비", "멍", "#B9E2FF", "#54B8FF", "base", "wasting.svg"),

    HOBBY(null, "취미",  "#D2C7FF", "#886AFF", "base", "hobby.svg"),
    GAME("취미", "게임", "#D2C7FF", "#886AFF", "base", "game.svg"),
    MOVIE("취미", "영화", "#D2C7FF", "#886AFF", "base", "movie.svg"),
    MUSIC("취미", "음악", "#D2C7FF", "#886AFF", "base", "music.svg"),
    INSTRUMENT("취미", "악기", "#D2C7FF", "#886AFF", "base", "instrument.svg"),

    ETC(null, "기타",  "#ECB8FF", "#D971FF", "base", "default.svg"),
    HOUSE_CHORE("기타", "집안일", "#ECB8FF", "#D971FF", "base", "cleaning.svg"),
    TRAVEL_TIME("기타", "이동시간", "#ECB8FF", "#D971FF", "base", "moving.svg");

    private final String parentName;
    private final String name;
    private final String color;
    private final String highlightColor;
    private final String iconGroup;
    private final String iconFilename;

    @Getter
    @RequiredArgsConstructor
    public enum Worker implements CategoryTemplate {
        JOB(null, "직장",  "#FFC5CC","#FF7184", "base", "job.svg"),
        WORK("직장", "업무", "#FFC5CC", "#FF7184", "base", "work.svg"),
        OVERTIME_WORK("직장", "야근", "#FFC5CC", "#FF7184", "base", "overwork.svg"),
        BUSINESS_TRIP("직장", "출장", "#FFC5CC", "#FF7184", "base", "businesstrip.svg"),
        COMPANY_DINNER("직장", "회식", "#FFC5CC", "#FF7184", "base", "companydinner.svg");

        private final String parentName;
        private final String name;
        private final String color;
        private final String highlightColor;
        private final String iconGroup;
        private final String iconFilename;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Student implements CategoryTemplate {
        STUDY(null, "학업", "#FFC5CC", "#FF7184", "base", "study.svg"),
        LECTURE("학업", "강의", "#FFC5CC", "#FF7184", "base", "lecture.svg"),
        ASSIGNMENT("학업", "과제", "#FFC5CC", "#FF7184", "base", "assignment.svg"),
        GROUP_STUDY("학업", "스터디", "#FFC5CC", "#FF7184", "base", "groupstudy.svg"),
        GROUP_WORK("학업", "팀플", "#FFC5CC", "#FF7184", "base", "groupwork.svg"),
        STUDY_FOR_EXAM("학업", "시험공부", "#FFC5CC", "#FF7184", "base", "studyforexam.svg");

        private final String parentName;
        private final String name;
        private final String color;
        private final String highlightColor;
        private final String iconGroup;
        private final String iconFilename;
    }
}
