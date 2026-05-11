package com.features.auth.presentation


import com.core.data.utils.Log
import com.features.auth.domain.model.LoginType
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.presentation.model.AuthState
import com.features.base.presentation.model.BaseViewModel

class AuthWelcomeViewModel
    : BaseViewModel<AuthState, AuthEvents, AuthEffects>(AuthState()) {

    override fun onEvent(event: AuthEvents) {

        when(event) {

            is AuthEvents.OnClickLogin -> {
                when (event.type) {
                    LoginType.PHONE -> {
                        sendEffect(AuthEffects.NavigateToLogin(LoginType.PHONE))
                    }

                    LoginType.YANDEX -> {
                        sendEffect(AuthEffects.NavigateToLogin(LoginType.YANDEX))
                    }
                }
            }

            AuthEvents.OnBackClicked -> {
                sendEffect(AuthEffects.NavigateBack)
            }

            else -> {}

        }

    }
}