package com.features.base.presentation.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<STATE, EVENT, EFFECT>(
    initialState: STATE,
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<STATE> = _state

    private val _effect = MutableSharedFlow<EFFECT>(replay = 1)
    val effect = _effect.asSharedFlow()

    abstract fun onEvent(event: EVENT)

    protected fun updateState(reducer: (STATE) -> STATE) = _state.update { reducer(it) }

    protected fun sendEffect(effect: EFFECT) {
        _effect.tryEmit(effect)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun clearEffects() = _effect.resetReplayCache()
}
