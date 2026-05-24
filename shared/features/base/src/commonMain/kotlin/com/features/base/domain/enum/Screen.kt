package com.features.base.domain.enum

interface Destination {
    val route: String
}

enum class Screen: Destination {
    SPLASH,
    WELCOME,
    PHONE,
    CODE,
    ONBOARDING,
    MAIN,
    LECTURE_LIST,
    LECTURE_DETAIL;

    override val route: String
        get() = name
}

enum class Graph(val screens: List<Screen>): Destination {
    AUTH(listOf(Screen.WELCOME, Screen.PHONE, Screen.CODE)),

    LECTURES(listOf(Screen.LECTURE_LIST, Screen.LECTURE_DETAIL));

    override val route: String
        get() = name
}