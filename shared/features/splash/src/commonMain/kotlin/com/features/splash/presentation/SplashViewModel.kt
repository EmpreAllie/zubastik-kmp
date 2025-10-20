package com.features.splash.presentation

import com.features.base.domain.enum.Screen
import com.features.base.presentation.model.BaseViewModel
import com.features.splash.domain.SplashRepository
import com.features.splash.presentation.model.SplashEffects
import com.features.splash.presentation.model.SplashEvents
import com.features.splash.presentation.model.SplashState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: SplashRepository) :
    BaseViewModel<SplashState, SplashEvents, SplashEffects>(SplashState()) {

    override fun onEvent(event: SplashEvents) {
        when (event) {
            SplashEvents.Initialize -> loadAndNavigate()
            SplashEvents.OnCloseDialog -> clearErrorText()
        }
    }

    private fun clearErrorText() = updateState {
        it.copy(errorText = null)
    }


    private fun loadAndNavigate() = viewModelScope.launch {
        delay(1000)

        val isAuthenticated = repository.isAuthenticated()

        val screen = if (isAuthenticated) Screen.MAIN
        else Screen.AUTHORIZATION

        sendEffect(SplashEffects.NavigateToScreen(screen))
    }
}