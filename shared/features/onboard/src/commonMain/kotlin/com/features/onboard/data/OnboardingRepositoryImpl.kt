package com.features.onboard.data

import com.features.onboard.domain.OnboardingAction
import com.features.onboard.domain.OnboardingRepository
import com.features.onboard.presentation.model.state.OnboardingStep
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OnboardingRepositoryImpl: OnboardingRepository {
    override fun sequence(step: OnboardingStep): Flow<OnboardingAction> = flow {
        when(step) {
            OnboardingStep.ASK_NAME -> {
                emit(OnboardingAction.ShowNextZubMessage)
                delay(1000)
                emit(OnboardingAction.ShowUserReply(OnboardingStep.ASK_NAME))
            }

            OnboardingStep.ASK_AGE -> {
                delay(1000)
                emit(OnboardingAction.ShowNextZubMessage)
                delay(1000)
                emit(OnboardingAction.ShowUserReply(OnboardingStep.ASK_AGE))
            }

            OnboardingStep.ASK_BRUSHING -> {
                delay(1000)
                emit(OnboardingAction.ShowNextZubMessage)
                delay(1000)
                emit(OnboardingAction.OpenBrushingDialog)
            }

            OnboardingStep.COMPLETE -> {
                delay(500)
                emit(OnboardingAction.ShowFinalUserMessage(0))
            }
        }
    }
}