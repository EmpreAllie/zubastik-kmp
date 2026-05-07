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
    MAIN;

    override val route: String
        get() = name
}

enum class Graph(val screens: List<Screen>): Destination {
    AUTH(listOf(Screen.WELCOME, Screen.PHONE, Screen.CODE));

    override val route: String
        get() = name
}