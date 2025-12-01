package com.features.base.presentation.model

import com.features.base.domain.model.Error

open class BaseState(
    open val isLoading: Boolean,
    open val error: Error?
)