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
                        onEvent(AuthEvents.OnCodeVerificationStarted)
                    }
                }
            }


            is AuthEvents.OnCodeVerificationStarted -> {
                verifyCode()
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

    fun onCodeScreenEntered() {
        onEvent(AuthEvents.OnStartResendCodeTimer)
    }

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


    private fun verifyCode() {
        viewModelScope.launch {
            updateState {it.copy(isLoading = true)}

            val phone = state.value.countryCode + state.value.phoneNumber
            val code = state.value.verificationCode
            val isSuccess = repository.verifyCode(phone, code)

            updateState {
                it.copy(
                    isLoading = false,
                    verificationStatus = if (isSuccess) VerificationStatus.SUCCESS else VerificationStatus.ERROR
                )
            }

            if (isSuccess) {
                sendEffect(AuthEffects.NavigateToMain)
            }

        }
    }


    // обновляем наш State, присваивая параметру error значение null
    private fun clearErrorText() = updateState {
        it.copy(error = null)
    }
}