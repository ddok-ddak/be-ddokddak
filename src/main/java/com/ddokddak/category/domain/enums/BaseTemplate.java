package com.ddokddak.category.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BaseTemplate implements CategoryTemplate {

    GROWTH(null, "성장",  "#FFCDA0", "#FFA24F", "default.jpg"),
    READING("성장", "독서", "#FFCDA0", "#FFA24F", "reading.jpg"),
    LECTURE("성장", "강의", "#FFCDA0", "#FFA24F", "lecture.jpg"),
    CERTIFICATION("성장", "자격증", "#FFCDA0", "#FFA24F", "certificate.jpg"),

    RELATIONSHIP(null, "관계",  "#FFE49F", "#FFC32B", "default.jpg"),
    FRIENDS("관계", "친구", "#FFE49F", "#FFC32B", "friend.jpg"),
    FAMILY("관계", "가족", "#FFE49F", "#FFC32B", "family.jpg"),
    LOVER("관계", "연인", "#FFE49F", "#FFC32B", "dating.jpg"),

    HEALTH(null, "건강",  "#C8F9AA", "#3EE423", "default.jpg"),
    SLEEP("건강", "잠", "#C8F9AA", "#3EE423", "sleep.jpg"),
    MEAL("건강", "식사", "#C8F9AA", "#3EE423", "meal.jpg"),
    WORKOUT("건강", "운동", "#C8F9AA", "#3EE423", "exercise.jpg"),

    WASTE(null, "낭비",  "#B9E2FF", "#54B8FF", "wasting.jpg"),
    SNS("낭비", "sns", "#B9E2FF", "#54B8FF", "sns.jpg"),
    WEB_SURFING("낭비", "웹서핑", "#B9E2FF", "#54B8FF", "internet.jpg"),
    MEDIA("낭비", "미디어", "#B9E2FF", "#54B8FF", "media.jpg"),
    DAY_DREAMING("낭비", "멍", "#B9E2FF", "#54B8FF", "wasting.jpg"),

    HOBBY(null, "취미",  "#D2C7FF", "#886AFF", "default.jpg"),
    GAME("취미", "게임", "#D2C7FF", "#886AFF", "game.jpg"),
    MOVIE("취미", "영화", "#D2C7FF", "#886AFF", "movie.jpg"),
    MUSIC("취미", "음악", "#D2C7FF", "#886AFF", "music.jpg"),
    INSTRUMENT("취미", "악기", "#D2C7FF", "#886AFF", "instrument.jpg"),

    ETC(null, "기타",  "#ECB8FF", "#D971FF", "default.jpg"),
    HOUSE_CHORE("기타", "집안일", "#ECB8FF", "#D971FF", "cleaning.jpg"),
    TRAVEL_TIME("기타", "이동시간", "#ECB8FF", "#D971FF", "moving.jpg");

    private final String parentName;
    private final String name;
    private final String color;
    private final String highlightColor;
    private final String iconName;

    @Getter
    @RequiredArgsConstructor
    public enum Worker implements CategoryTemplate {
        JOB(null, "직장",  "#FFC5CC","#FF7184", "default.jpg"),
        WORK("직장", "업무", "#FFC5CC", "#FF7184", "work.jpg"),
        OVERTIME_WORK("직장", "야근", "#FFC5CC", "#FF7184", "overwork.jpg"),
        BUSINESS_TRIP("직장", "출장", "#FFC5CC", "#FF7184", "businesstrip.jpg"),
        COMPANY_DINNER("직장", "회식", "#FFC5CC", "#FF7184", "companydinner.jpg");

        private final String parentName;
        private final String name;
        private final String color;
        private final String highlightColor;
        private final String iconName;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Student implements CategoryTemplate {
        LECTURE("학업", "강의", "#FFC5CC", "#FF7184", "lecture.jpg"),
        ASSIGNMENT("학업", "과제", "#FFC5CC", "#FF7184", "assignment.jpg"),
        STUDY_GROUP("학업", "스터디", "#FFC5CC", "#FF7184", "study.jpg"),
        GROUP_WORK("학업", "팀플", "#FFC5CC", "#FF7184", "default.jpg"),
        STUDY_FOR_EXAM("학업", "시험공부", "#FFC5CC", "#FF7184", "default.jpg");

        private final String parentName;
        private final String name;
        private final String color;
        private final String highlightColor;
        private final String iconName;
    }
}
