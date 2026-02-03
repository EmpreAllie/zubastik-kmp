package com.features.auth.presentation

import androidx.lifecycle.viewModelScope
import com.features.auth.domain.AuthRepository
import com.features.auth.domain.TimerRepository
import com.features.auth.domain.model.VerificationStatus
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.presentation.model.AuthState
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import com.features.base.presentation.model.BaseViewModel
import kotlinx.coroutines.launch


// ViewModel экрана с кодом подтверждения
class AuthCodeViewModel(
    private val repository: AuthRepository,
    private val timerRepository: TimerRepository
) : BaseViewModel<AuthState, AuthEvents, AuthEffects>(AuthState()) {

    init {
        println("ZUB_DEBUG: --- Создана AuthCodeViewModel ---")
        collectTimerUpdates()
    }


    // обработка событий только на экране с кодом
    override fun onEvent(event: AuthEvents) {
        when(event) {

            is AuthEvents.OnVerificationCodeChanged -> {
                onVerificationCodeChanged(event.code)
            }

            AuthEvents.OnResendCodeClicked -> {
                // repository.askServerForaNewCode();
                timerRepository.startTimer()
            }

            AuthEvents.OnStartResendCodeTimer -> {
                timerRepository.startTimer()
            }

            AuthEvents.OnBackClicked -> {
                sendEffect(AuthEffects.NavigateBack)
            }

            else -> {}
        }
    }



    // функция, которая вызывается при событии на экране с кодом "изменился введенный код"
    private fun onVerificationCodeChanged(newCode: String) {
        if (newCode.length <= 4 && newCode.all{it.isDigit()}) {
            updateState{
                it.copy(
                    verificationCode = newCode,
                    verificationStatus = VerificationStatus.NEUTRAL
                )
            }

            // когда введено 4 цифры
            if (newCode.length == 4) {
                verifyCode()
            }

        }
    }



    // обрабатываем результат из репозитория
    private fun verifyCode() = viewModelScope.launch {
        updateState { it.copy(isLoading = true) }

        val phone = state.value.phoneNumber.format()
        val code = state.value.verificationCode

        repository.verifyCode(phone, code).collect { result ->
            when (result) {
                is Result.Loading -> updateState {
                    it.copy(isLoading = true, error = null)
                }

                is Result.Failure -> updateState {
                    it.copy(isLoading = false, verificationStatus = VerificationStatus.ERROR)
                }

                is Result.Success -> {
                    updateState {
                        it.copy(
                            isLoading = false,
                            verificationStatus = VerificationStatus.SUCCESS,
                            error = null
                        )
                    }
                    sendEffect(AuthEffects.NavigateToMain)
                }

                is Result.ConnectionError -> updateState {
                    it.copy(
                        isLoading = false,
                        verificationStatus = VerificationStatus.ERROR,
                        error = Error.CONNECTION
                    )
                }

                is Result.TokenExpired -> updateState {
                    it.copy(isLoading = false, error = Error.TOKEN)
                }
            }
        }
    }



    private fun collectTimerUpdates() = viewModelScope.launch {
        timerRepository.secondsRemaining.collect { seconds ->
            updateState { it.copy(resendCodeTimerSeconds = seconds) }
        }
    }

}