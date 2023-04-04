package com.ddokddak.category.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CategoryTemplate {

    JOB(10, 0, "직장",  "#20FFD7"),
    WORK(11, 10, "업무", "#C2FFF4"),
    OVERTIME_WORK(12, 10, "야근", "#C2FFF4"),
    BUSINESS_TRIP(13, 10, "출장", "#C2FFF4"),
    COMPANY_DINNER(14, 10, "회식", "#C2FFF4"),

    GROWTH(20, 0, "성장",  "#20FFD7"),
    READING(21, 20, "독서", "#C2FFF4"),
    LECTURE(22, 20, "강의", "#C2FFF4"),
    CERTIFICATION(23, 20, "자격증", "#C2FFF4"),

    RELATIONSHIP(30, 0, "관계",  "#20FFD7"),
    FRIENDS(31, 30, "친구", "#C2FFF4"),
    FAMILY(32, 30, "가족", "#C2FFF4"),
    LOVER(33, 30, "연인", "#C2FFF4"),

    HEALTH(40, 0, "건강",  "#20FFD7"),
    SLEEP(41, 40, "잠", "#C2FFF4"),
    MEAL(42, 40, "식사", "#C2FFF4"),
    WORKOUT(43, 40, "운동", "#C2FFF4"),

    WASTE(50, 0, "낭비",  "#20FFD7"),
    SNS(51, 50, "sns", "#C2FFF4"),
    WEB_SURFING(52, 50, "웹서핑", "#C2FFF4"),
    MEDIA(53, 50, "미디어", "#C2FFF4"),
    DAY_DREAMING(54, 50, "멍", "#C2FFF4"),

    HOBBY(60, 0, "취미",  "#20FFD7"),
    GAME(61, 60, "게임", "#C2FFF4"),
    MOVIE(62, 60, "영화", "#C2FFF4"),
    MUSIC(63, 60, "음악", "#C2FFF4"),
    INSTRUMENT(64, 60, "악기", "#C2FFF4"),

    ETC(1, 0, "기타",  "#20FFD7"),
    HOUSE_CHORE(2, 1, "업무", "#C2FFF4"),
    TRAVEL_TIME(3, 1, "야근", "#C2FFF4");

    private final int id;
    private final int parentId;
    private final String name;
    private final String color;

}
