package com.features.auth.presentation.model

import com.features.auth.domain.model.LoginType


interface AuthEffects {
    data object NavigateToBack: AuthEffects
    data object NavigateToCodeInput: AuthEffects

    data object NavigateToMain: AuthEffects
    data class NavigateToLogin(val type: LoginType): AuthEffects
}