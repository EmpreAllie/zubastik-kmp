package com.features.auth.presentation.model

// эффекты, которые будут отражены на UI вследствие событий (Events)

interface AuthEffects {
    data object NavigateToPhoneInput: AuthEffects
    data object NavigateToYandexLogin: AuthEffects
    data object NavigateToCodeInput: AuthEffects
}