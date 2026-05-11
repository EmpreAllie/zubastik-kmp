package com.features.onboard.domain

import com.features.onboard.presentation.model.state.OnboardingStep
import kotlinx.coroutines.flow.Flow
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import com.features.teeth.domain.model.Tooth

interface OnboardingRepository {
    fun sequence(step: OnboardingStep) : Flow<OnboardingAction>

    suspend fun sendProfileInfoToServer(
        name: String,
        age: Int,
        timesBrushing: List<String>
    ): Result<Unit, Error>

    suspend fun sendTeethInfoToServer(
        teeth: List<Tooth>
    ): Result<Unit, Error>
}