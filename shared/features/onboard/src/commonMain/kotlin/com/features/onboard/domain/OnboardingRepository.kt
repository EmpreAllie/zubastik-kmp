package com.features.onboard.domain

import com.features.onboard.presentation.model.state.OnboardingStep
import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {
    fun sequence(step: OnboardingStep) : Flow<OnboardingAction>
}