package com.features.onboard.domain

import com.features.onboard.presentation.model.state.OnboardingStep
import kotlinx.coroutines.flow.Flow
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import com.features.teeth.domain.model.Tooth

interface OnboardingRepository {
    fun sequence(step: OnboardingStep) : Flow<OnboardingAction>

    fun sendProfileInfoToServer(
        name: String,
        age: Int,
    ): Flow<Result<Unit, Error>>

    fun sendBrushingScheduleToServer(
        timesBrushing: List<String>
    ): Flow<Result<Unit, Error>>

    fun sendTeethInfoToServer(
        teeth: List<Tooth>
    ): Flow<Result<Unit, Error>>
}