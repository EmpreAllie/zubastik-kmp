package com.features.splash.presentation.model

import com.features.base.domain.enum.Screen

sealed interface SplashEffects {
    data class NavigateToScreen(val screen: Screen): SplashEffects
}