package com.features.auth.presentation

import androidx.lifecycle.viewModelScope
import com.features.auth.domain.AuthRepository
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.presentation.model.AuthState
import com.features.base.domain.Result
import com.features.base.presentation.model.BaseViewModel
import kotlinx.coroutines.launch

// ViewModel экрана с вводом телефона, отвечает только за этот самый ввод телефона
class AuthPhoneViewModel(
    private val repository: AuthRepository
) : BaseViewModel<AuthState, AuthEvents, AuthEffects>(AuthState()) {

    // метод onEvent ловит события только с экрана ввода телефона
    override fun onEvent(event: AuthEvents) {

        when(event) {
            is AuthEvents.OnGotoCodeClicked -> {
                if (state.value.phoneNumber.isCorrect()) {
                    sendPhoneNumberToServer()
                }
            }

            is AuthEvents.OnPhoneNumberChanged -> {
                updateState {it.copy(phoneNumber = event.phoneNumber)}
            }

            AuthEvents.OnBackClicked -> {
                sendEffect(AuthEffects.NavigateBack)
            }

            else -> {}
        }

    }



    private fun sendPhoneNumberToServer() = viewModelScope.launch {
        updateState { it.copy(isLoading = true) }
        val formattedPhone = state.value.phoneNumber.toE164()

        when (val result = repository.sendPhoneNumberToServer(formattedPhone)) {

            is Result.Success -> {
                updateState { it.copy(isLoading = false) }

                repository.setPhone(state.value.phoneNumber.number)
                // repository.setPhone(state.value.phoneNumber.toE164())

                sendEffect(AuthEffects.NavigateToCodeInput)
            }

            is Result.Failure -> {
                updateState {
                    it.copy(
                        isLoading = false,
                        error = result.error
                    )
                }
            }

            else -> {}

        }
    }
}