package com.features.teeth.presentation.model

import com.features.teeth.domain.model.Tooth
import com.features.teeth.domain.model.ToothStatus

sealed interface TeethEvents {
    data class OnToothClicked(val id: Int): TeethEvents
    data class OnTeethUpdated(val teeth: List<Tooth>): TeethEvents
    data object OnEditClicked: TeethEvents
    data object OnDismissDialog: TeethEvents

    data object OnCloseErrorDialog: TeethEvents

    data class OnToothStatusChanged(
        val toothId: Int,
        val status: ToothStatus,
        val note: String
    ): TeethEvents
}