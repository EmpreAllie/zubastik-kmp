package com.features.splash.presentation

import androidx.lifecycle.viewModelScope
import com.features.base.domain.enum.Graph
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
        init {
            loadAndNavigate()
        }

    override fun onEvent(event: SplashEvents) {
        when (event) {
            SplashEvents.OnCloseDialog -> clearErrorText()
        }
    }

    private fun clearErrorText() = updateState {
        it.copy(error = null)
    }


    private fun loadAndNavigate() = viewModelScope.launch {
        delay(1500)

        val isAuthenticated = repository.isAuthenticated()

        if (isAuthenticated) {
            sendEffect(SplashEffects.NavigateToScreen(Screen.MAIN))
        } else {
            //sendEffect(SplashEffects.NavigateToScreen(Screen.AUTH))
            sendEffect(SplashEffects.NavigateToScreen(Graph.AUTH))
        }
    }
}