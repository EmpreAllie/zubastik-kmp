package com.features.teeth.domain

import kotlinx.coroutines.flow.StateFlow
import com.features.teeth.domain.model.Tooth
import com.features.teeth.domain.model.ToothStatus
import kotlinx.coroutines.flow.Flow
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error

interface TeethRepository {
    val teeth: StateFlow<List<Tooth>>
    fun getToothById(id: Int): Tooth?
    fun updateToothStatus(id: Int, newStatus: ToothStatus, note: String)
    fun loadTeethFromServer(): Flow<Result<Unit, Error>>
}