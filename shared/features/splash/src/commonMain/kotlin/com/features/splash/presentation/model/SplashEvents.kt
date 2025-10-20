package com.features.splash.presentation.model

sealed interface SplashEvents {
    data object Initialize: SplashEvents
    data object OnCloseDialog: SplashEvents
}