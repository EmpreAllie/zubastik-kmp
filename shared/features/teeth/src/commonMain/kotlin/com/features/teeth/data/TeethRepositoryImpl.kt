package com.features.teeth.data

import com.features.teeth.domain.TeethRepository
import com.features.teeth.domain.model.Tooth
import com.features.teeth.domain.model.ToothStatus
import com.features.teeth.domain.model.ToothType
import com.network.api.apis.ProfileApi
import com.network.api.models.ToothUpdateItem
import com.network.api.models.UpdateTeethRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class TeethRepositoryImpl(
    private val profileApi: ProfileApi
): TeethRepository {
    private val _teeth = MutableStateFlow(emptyList<Tooth>())
    override val teeth: StateFlow<List<Tooth>> = _teeth.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val serverTeeth = profileApi.apiV1ProfileTeethGet().body()
                _teeth.value = serverTeeth.map { serverTooth ->
                    Tooth(
                        id = serverIndexToToothId(serverTooth.toothIndex ?: 0),
                        type = ToothType.fromPosition(serverTooth.toothIndex ?: 0),
                        status = serverStateToToothStatus(serverTooth.toothState),
                        note = serverTooth.toothNote
                    )
                }
            } catch (e: Exception) {

            }
        }
    }

    override fun getToothById(id: Int): Tooth? {
        return _teeth.value.find { it.id == id }
    }

    override fun updateToothStatus(id: Int, newStatus: ToothStatus, note: String) {
        _teeth.value = _teeth.value.map { tooth ->
            if (tooth.id == id)
                tooth.copy(status = newStatus, note = note)
            else
                tooth
        }

        // обновление зуба на сервере через PATCH
        CoroutineScope(Dispatchers.IO).launch {
            try {
                profileApi.apiV1ProfileTeethPatch(
                    UpdateTeethRequest(
                        teeth = _teeth.value.map { tooth ->
                            ToothUpdateItem(
                                toothState = when(tooth.status) {
                                    ToothStatus.HEALTHY -> ToothUpdateItem.ToothState.HEALTHY
                                    ToothStatus.PROBLEMATIC -> ToothUpdateItem.ToothState.PROBLEMATIC
                                    ToothStatus.MISSING -> ToothUpdateItem.ToothState.MISSING
                                },
                                toothNote = tooth.note
                            )
                        }
                    )
                )
            } catch (e: Exception) {

            }
        }
    }

    private fun serverIndexToToothId(index: Int): Int {
        val quarter = index / 8 + 1
        val pos = index % 8 + 1
        return quarter * 10 + pos
    }

    private fun serverStateToToothStatus(state: com.network.api.models.Tooth.ToothState?): ToothStatus = when(state) {
        com.network.api.models.Tooth.ToothState.PROBLEMATIC -> ToothStatus.PROBLEMATIC
        com.network.api.models.Tooth.ToothState.MISSING -> ToothStatus.MISSING
        else -> ToothStatus.HEALTHY
    }
}