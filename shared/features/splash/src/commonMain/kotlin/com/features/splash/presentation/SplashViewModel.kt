package com.features.splash.presentation

import com.features.base.presentation.model.BaseViewModel
import com.features.base.presentation.model.StateFlow
import com.features.splash.domain.SplashRepository
import com.features.splash.presentation.model.SplashEvents
import com.features.splash.presentation.model.SplashState
import com.features.splash.presentation.model.SplashNavigationEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: SplashRepository) :
    BaseViewModel<SplashState, SplashEvents>(SplashState()) {

    // SharedFlow для навигации
    private val _navigationEvent = MutableSharedFlow<SplashNavigationEvent>()
    val navigationEvent: SharedFlow<SplashNavigationEvent> = _navigationEvent

    override fun onEvent(events: SplashEvents) = when (events) {
        SplashEvents.OnBack -> {}
        SplashEvents.OnCloseDialog -> clearErrorText()
    }

    private fun clearErrorText() = updateState {
        it.copy(errorText = null)
    }


    // функция для определения следующего экрана, вызывается 1 раз при запуске приложения
    fun loadAndNavigate() {
        viewModelScope.launch {
            delay(1000)

            val isAuthenticated = repository.isAuthenticated()

            val targetRoute = if (isAuthenticated) {
                SplashNavigationEvent.NavigateToMain
            } else {
                SplashNavigationEvent.NavigateToAuth
            }

            // "выбросить" новое значение наружу, чтобы все слушатели его поймали
            _navigationEvent.emit(targetRoute)
        }
    }
}