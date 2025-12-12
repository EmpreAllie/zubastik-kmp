package com.features.auth.presentation

import androidx.lifecycle.viewModelScope
import com.features.auth.domain.AuthRepository
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.presentation.model.AuthState
import com.features.auth.presentation.model.VerificationStatus
import com.features.base.presentation.model.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import com.features.base.domain.Result
import com.features.base.domain.model.Error


class AuthViewModel(private val repository: AuthRepository) :
    BaseViewModel<AuthState, AuthEvents, AuthEffects>(AuthState()) {

    // ловим события от UI
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

                val isError = !event.number.startsWith("9")

                updateState {
                    it.copy(
                        phoneNumber = event.number,
                        countryCode = event.countryCode,
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

            is AuthEvents.OnVerificationCodeChanged -> {

                val newCode = event.code

                if (newCode.length <= 4 && newCode.all {it.isDigit()}) {
                    updateState {
                        it.copy(
                            verificationCode = newCode,
                            verificationStatus = VerificationStatus.NEUTRAL
                        )
                    }

                    if (newCode.length == 4) {
                        verifyCode()
                    }
                }
            }


            // первое событие
            is AuthEvents.OnStartResendCodeTimer -> {
                startResendCodeTimer()
            }

            // последующие события
            is AuthEvents.OnResendCodeClicked -> {
                // repository.sendPhoneNumberToServer()
                startResendCodeTimer()
            }
        }
    }

    // при заходе на экран с кодом вызываем Event, который ловит onEvent()
    fun onCodeScreenEntered() {
        onEvent(AuthEvents.OnStartResendCodeTimer)
    }

    // запуск корутины отсчета от 59 до 0
    private fun startResendCodeTimer() {
        viewModelScope.launch() {
            flow {
                for (i in 59 downTo 0) {
                    emit(i)
                    delay(1000)
                }
            }.collect { seconds ->
                updateState { it.copy(resendCodeTimerSeconds = seconds) }
            }
        }
    }

    private fun sendPhoneNumberToServer() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            val phone = state.value.countryCode + state.value.phoneNumber

            when (repository.sendPhoneNumberToServer(phone)) {
                is Result.Success -> {
                    updateState { it.copy(isLoading = false) }

                    sendEffect(AuthEffects.NavigateToCodeInput)
                }

                is Result.Failure -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            isPhoneNumberError = true,
                            errorMessage = ""
                        )
                    }
                }

                else -> {

                }

            }
        }
    }

    // обрабатываем результат из репозитория
    private fun verifyCode() {
        viewModelScope.launch {
            updateState {it.copy(isLoading = true)}

            val phone = state.value.countryCode + state.value.phoneNumber
            val code = state.value.verificationCode

            // получаем результат из репозитория
            repository.verifyCode(phone,code).collect { result ->
                when(result) {
                    is Result.Loading -> updateState { it.copy(isLoading = true) }
                    is Result.Failure -> updateState { it.copy(isLoading = false, verificationStatus = VerificationStatus.SUCCESS) }
                    is Result.Success -> updateState { it.copy(isLoading = false, verificationStatus = VerificationStatus.SUCCESS) }
                    is Result.ConnectionError -> updateState { it.copy(isLoading = false, verificationStatus = VerificationStatus.ERROR, isConnectionError = true) }
                    is Result.TokenExpired -> updateState { it.copy(isLoading = false) }
                }
            }
        }
    }

    // обновляем наш State, присваивая параметру error значение nullё
    private fun clearErrorText() = updateState {
        it.copy(error = null)
    }
}