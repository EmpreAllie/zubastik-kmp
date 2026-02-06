package com.features.auth.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object TimerRepository {
    private const val TIMER_DURATION_SECONDS = 59

    private val _secondsRemaining = MutableStateFlow(0)
    val secondsRemaining = _secondsRemaining.asStateFlow()

    private var timerJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default)




    fun startTimer() {
        timerJob?.cancel()

        timerJob = scope.launch {
            for (i in TIMER_DURATION_SECONDS downTo 0) {
                _secondsRemaining.value = i
                delay(1000)
            }
        }

    }




    fun stopTimer() {
        timerJob?.cancel()
        _secondsRemaining.value = 0
    }

}