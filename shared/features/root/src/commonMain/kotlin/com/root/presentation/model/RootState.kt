package com.root.presentation.model

import com.features.base.domain.enum.Destination
import com.features.base.domain.enum.Screen
import com.features.base.presentation.model.BaseState

data class RootState(
    val destination: Destination? = null,
    val isPopScreen: Boolean = false,
    var arguments: List<String> = emptyList(),
): BaseState(isLoading = false, error = null)