package com.features.auth.presentation

import androidx.lifecycle.viewModelScope
import com.features.auth.domain.AuthRepository
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.presentation.model.AuthState
import com.features.base.domain.Result
import com.features.base.presentation.model.BaseViewModel
import kotlinx.coroutines.launch
import com.features.base.domain.model.error.Error

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

            AuthEvents.OnCloseErrorDialog -> updateState { it.copy(error = null) }

            else -> {}
        }

    }



    private fun sendPhoneNumberToServer() = viewModelScope.launch {
        val formattedPhone = state.value.phoneNumber.toE164()

        repository.sendPhoneNumberToServer(formattedPhone).collect { result ->
            when(result) {
                Result.Loading -> updateState { it.copy(isLoading = true) }

                is Result.Success -> {
                    updateState { it.copy(isLoading = false) }
                    repository.setPhone(state.value.phoneNumber.number)
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

                Result.ConnectionError -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            error = Error.CONNECTION
                        )
                    }
                }

                else -> {}
            }
        }
    }
}