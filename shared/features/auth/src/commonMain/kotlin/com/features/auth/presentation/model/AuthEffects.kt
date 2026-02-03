package com.features.auth.presentation.model

import com.features.auth.domain.model.LoginType


interface AuthEffects {
    data object NavigateBack: AuthEffects
    data class NavigateToCodeInput(val phone: String): AuthEffects

    data object NavigateToMain: AuthEffects
    data class NavigateToLogin(val type: LoginType): AuthEffects
}