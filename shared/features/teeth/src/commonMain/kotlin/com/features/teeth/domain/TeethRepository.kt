package com.features.teeth.domain

import kotlinx.coroutines.flow.StateFlow
import com.features.teeth.domain.model.Tooth
import com.features.teeth.domain.model.ToothStatus

interface TeethRepository {
    val teeth: StateFlow<List<Tooth>>
    fun getToothById(id: Int): Tooth?
    fun updateToothStatus(id: Int, newStatus: ToothStatus, note: String)
}