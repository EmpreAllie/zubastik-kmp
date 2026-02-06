package com.features.splash.presentation.model

import com.features.base.domain.enum.Destination
import com.features.base.domain.enum.Screen

sealed interface SplashEffects {
    data class NavigateToScreen(val screen: Destination): SplashEffects
}