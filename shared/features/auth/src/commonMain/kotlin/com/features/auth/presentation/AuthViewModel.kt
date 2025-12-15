package com.features.auth.presentation

import androidx.lifecycle.viewModelScope
import com.features.auth.domain.AuthRepository
import com.features.auth.domain.TimerRepository
import com.features.auth.domain.model.VerificationStatus
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.presentation.model.AuthState
import com.features.base.domain.Result
import com.features.base.domain.model.error.AuthErrorType
import com.features.base.domain.model.error.Error
import com.features.base.presentation.model.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


class AuthViewModel( // TODO: У тебя три разных экрана и у каждого экрана должен быть свой ViewModel, но может быть общий репозиторий. Например: AuthWelcomeViewModel, AuthConfirmViewModel, AuthPhoneViewModel, НО: !AuthRepository!
    private val repository: AuthRepository,
    private val timerRepository: TimerRepository
) : BaseViewModel<AuthState, AuthEvents, AuthEffects>(AuthState()) {

    init {
        collectTimerUpdates()
    }

    // ловим события от UI
    override fun onEvent(event: AuthEvents) {
        when (event) {
            AuthEvents.OnGotoCodeClicked -> sendEffect(AuthEffects.NavigateToCodeInput)
            AuthEvents.OnBackClicked -> sendEffect(AuthEffects.NavigateToBack)
            AuthEvents.OnCloseDialog -> clearErrorText() // TODO: Anton: У data object не надо писать is, у data class - надо
            AuthEvents.OnResendCodeClicked -> {
                // repository.sendPhoneNumberToServer()
                // startResendCodeTimer()
                timerRepository.startTimer()
            }

            AuthEvents.OnStartResendCodeTimer -> {
                // startResendCodeTimer()
                timerRepository.startTimer()
            }

            is AuthEvents.OnClickLogin -> sendEffect(AuthEffects.NavigateToLogin(event.type))


            is AuthEvents.OnVerificationCodeChanged -> { // TODO: Anton: Вынести в метод класс

                val newCode = event.code

                if (newCode.length <= 4 && newCode.all { it.isDigit() }) {
                    updateState {
                        it.copy(
                            verificationCode = newCode,
                            verificationStatus = VerificationStatus.NEUTRAL
                        )
                    }

                    if (newCode.length == 4) verifyCode()
                }
            }

            is AuthEvents.OnPhoneNumberChanged -> updateState {
                it.copy(phoneNumber = event.phoneNumber)
            }
        }
    }

    // при заходе на экран с кодом вызываем Event, который ловит onEvent()
    fun onCodeScreenEntered() {
        onEvent(AuthEvents.OnStartResendCodeTimer)
    }

    // запуск корутины отсчета от 59 до 0
    // TODO: Anton:TimerRepository
    private fun startResendCodeTimer() = viewModelScope.launch() {
        flow {
            for (i in 59 downTo 0) {
                emit(i)
                delay(1000)
            }
        }.collect { seconds ->
            updateState { it.copy(resendCodeTimerSeconds = seconds) }
        }
    }


    private fun sendPhoneNumberToServer() = viewModelScope.launch {
        updateState { it.copy(isLoading = true) }
        val phone = state.value.phoneNumber.format()

        when (repository.sendPhoneNumberToServer(phone)) {
            is Result.Success -> {
                updateState { it.copy(isLoading = false) }

                sendEffect(AuthEffects.NavigateToCodeInput)
            }

            is Result.Failure -> {
                updateState {
                    it.copy(
                        isLoading = false,
                        error = Error.AUTH(AuthErrorType.PHONE), // TODO: Anton: Получать ошибку с репозитория
                    )
                }
            }

            else -> {

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

                is Result.Success -> updateState {
                    it.copy(
                        isLoading = false,
                        verificationStatus = VerificationStatus.SUCCESS,
                        error = null
                    )
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


    // обновляем наш State, присваивая параметру error значение nullё
    private fun clearErrorText() = updateState {
        it.copy(error = null)
    }

    private fun collectTimerUpdates() = viewModelScope.launch {
        timerRepository.secondsRemaining.collect { seconds ->
            updateState { it.copy(resendCodeTimerSeconds = seconds) }
        }
    }

}