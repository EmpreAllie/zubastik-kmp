package com.features.onboard.data

import com.features.base.domain.Result
import com.features.onboard.domain.OnboardingAction
import com.features.onboard.domain.OnboardingRepository
import com.features.onboard.presentation.model.state.OnboardingStep
import com.network.api.apis.ProfileApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import com.features.base.domain.model.error.Error
import com.features.teeth.domain.model.Tooth
import com.features.teeth.domain.model.ToothStatus
import com.network.api.models.ToothUpdateItem
import com.network.api.models.UpdateBrushScheduleRequest
import com.network.api.models.UpdateProfileRequest
import com.network.api.models.UpdateTeethRequest
import com.network.data.exception.CustomResponseException
import com.network.domain.model.isConnectionException
import kotlinx.coroutines.flow.flowOn

class OnboardingRepositoryImpl(
    private val profileApi: ProfileApi
): OnboardingRepository {

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

    override fun sendProfileInfoToServer(
        name: String,
        age: Int,
    ): Flow<Result<Unit, Error>> = flow {
        emit(Result.Loading)

        try {
            profileApi.apiV1ProfilePatch(
                UpdateProfileRequest(
                    name = name,
                    age = age
                )
            )
            emit(Result.Success(Unit))

        } catch (e: CustomResponseException) {
            emit(Error.OTHER(e.message.orEmpty()).toResult())

        } catch (e: Exception) {
            val result = if (e.isConnectionException())
                Result.ConnectionError
            else
                Error.OTHER(e.message.orEmpty()).toResult()

            // println("ZUB_DEBUG error: ${e.message}")

            emit(result)
        }
    }.flowOn(Dispatchers.IO)

    override fun sendTeethInfoToServer(teeth: List<Tooth>)
    : Flow<Result<Unit, Error>> = flow {
        emit(Result.Loading)
        try {

            profileApi.apiV1ProfileTeethPatch(
                UpdateTeethRequest(
                    teeth = teeth.map { tooth ->
                        ToothUpdateItem(
                            toothState = when (tooth.status) {
                                ToothStatus.HEALTHY -> ToothUpdateItem.ToothState.HEALTHY
                                ToothStatus.PROBLEMATIC -> ToothUpdateItem.ToothState.PROBLEMATIC
                                ToothStatus.MISSING -> ToothUpdateItem.ToothState.MISSING },
                            toothNote = tooth.note
                        )
                    }
                )
            )
            emit(Result.Success(Unit))

        } catch (e: CustomResponseException) {
            emit(Error.OTHER(e.message.orEmpty()).toResult())
        }
        catch (e: Exception) {
            val result = if (e.isConnectionException())
                Result.ConnectionError
            else
                Error.OTHER(e.message.orEmpty()).toResult()

            emit(result)
        }
    }.flowOn(Dispatchers.IO)

    override fun sendBrushingScheduleToServer(timesBrushing: List<String>)
    : Flow<Result<Unit, Error>> = flow<Result<Unit, Error>> {
        emit(Result.Loading)

        try {
            profileApi.apiV1ProfileBrushSchedulePut(
                UpdateBrushScheduleRequest(
                    timesBrush = timesBrushing
                )
            )
            emit(Result.Success(Unit))
        } catch (e: CustomResponseException) {
            emit(Error.OTHER(e.message.orEmpty()).toResult())
        } catch (e: Exception) {
            val result = if (e.isConnectionException())
                Result.ConnectionError
            else
                Error.OTHER(e.message.orEmpty()).toResult()

            // println("ZUB_DEBUG error: ${e.message}")

            emit(result)
        }
    }.flowOn(Dispatchers.IO)
}
