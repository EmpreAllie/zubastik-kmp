package com.features.splash.presentation.model

// одноразовый Event для навигации из SplashScreen

sealed interface SplashNavigationEvent {
    data object NavigateToAuth : SplashNavigationEvent
    data object NavigateToMain : SplashNavigationEvent
}