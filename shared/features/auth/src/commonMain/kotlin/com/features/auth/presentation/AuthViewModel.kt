package com.features.auth.presentation

import com.features.auth.domain.AuthRepository
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.presentation.model.AuthState
import com.features.base.presentation.model.BaseViewModel

class AuthViewModel(private val repository: AuthRepository) :
    BaseViewModel<AuthState, AuthEvents, AuthEffects>(AuthState()) {


    override fun onEvent(event: AuthEvents) {

        when(event) {

            is AuthEvents.OnLoginClicked -> {
                sendEffect(AuthEffects.NavigateToPhoneInput)
            }

            is AuthEvents.OnYandexLoginClicked -> {
                sendEffect(AuthEffects.NavigateToYandexLogin)
            }

            is AuthEvents.OnCloseDialog -> {
                clearErrorText()
            }

            // обновляем State, когда меняется введенный номер телефона
            is AuthEvents.OnPhoneNumberChanged -> {

                val isError = event.number.isNotEmpty() && !event.number.startsWith("9")

                updateState {
                    it.copy(
                        phoneNumber = event.number,
                        isPhoneNumberError = isError
                    )
                }
            }

            is AuthEvents.OnGotoCodeClicked -> {
                sendEffect(AuthEffects.NavigateToCodeInput)
            }

            is AuthEvents.OnBackClicked -> {
                sendEffect(AuthEffects.NavigateToPhoneInput)
            }
        }
    }



    // обновляем наш State, присваивая параметру error значение null
    private fun clearErrorText() = updateState {
        it.copy(error = null)
    }
}