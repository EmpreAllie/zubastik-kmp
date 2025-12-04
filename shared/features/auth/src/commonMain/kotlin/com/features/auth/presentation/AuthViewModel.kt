package com.features.auth.presentation

import com.features.auth.domain.AuthRepository
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.presentation.model.AuthState
import com.features.base.presentation.model.BaseViewModel

class AuthViewModel(private val repository: AuthRepository) :
    BaseViewModel<AuthState, AuthEvents, AuthEffects>(AuthState()) {

    // слушатель событий (Events) во флоу авторизации
    // вместе с этим - еще и функция из абстрактного класса, которую обязательно надо переопределить
    override fun onEvent(event: AuthEvents) {

        // типо "когда происходит какое-то событие"
        when(event) {

            // то отправляется эффект, который подхватывается слушателем из основной функции с экраном
            // (в данном случае - AuthWelcomeScreen())
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
                var newNumber = event.number

                newNumber = newNumber.filter { it.isDigit() }
                if (newNumber.length > 10) {
                    newNumber = newNumber.substring(0, 10)
                }

                val isError = newNumber.isNotEmpty() && !newNumber.startsWith("9")

                updateState {
                    it.copy(
                        phoneNumber = newNumber,
                        isPhoneNumberError = isError
                    )
                }
            }

            is AuthEvents.OnGotoCodeClicked -> {
                sendEffect(AuthEffects.NavigateToCodeInput)
            }
        }
    }



    // обновляем наш State, присваивая параметру error значение null
    private fun clearErrorText() = updateState {
        it.copy(error = null)
    }
}