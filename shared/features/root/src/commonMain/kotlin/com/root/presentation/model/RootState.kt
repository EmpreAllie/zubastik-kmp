package com.root.presentation.model

import com.features.base.domain.enum.Destination
import com.features.base.presentation.model.BaseState

data class RootState(
    val destination: Destination? = null,
    val screenStack: List<Destination> = emptyList()
): BaseState(isLoading = false, error = null)