package com.features.base.domain.enum

interface Destination {
    val route: String
}

enum class Screen: Destination {
    SPLASH,
    WELCOME,
    PHONE,
    CONFIRM,
    MAIN;

    override val route: String
        get() = name
}

enum class Graph(val screens: List<Screen>): Destination {
    AUTH(listOf(Screen.WELCOME, Screen.PHONE, Screen.CONFIRM));

    override val route: String
        get() = name
}