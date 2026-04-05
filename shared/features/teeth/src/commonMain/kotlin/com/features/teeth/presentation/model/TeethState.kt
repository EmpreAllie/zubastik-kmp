package com.features.teeth.presentation.model

import com.features.base.domain.model.error.Error
import com.features.base.presentation.model.BaseState
import com.features.teeth.domain.model.Tooth

data class TeethState(
    val teeth: List<Tooth> = emptyList(),
    val selectedToothId: Int? = null,
    val isEditDialogVisible: Boolean = false,

    override val isLoading: Boolean = false,
    override val error: Error? = null
) : BaseState(
    isLoading = isLoading,
    error = error
)