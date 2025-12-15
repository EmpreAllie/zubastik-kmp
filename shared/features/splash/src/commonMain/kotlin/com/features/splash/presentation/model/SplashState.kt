package com.features.splash.presentation.model

import com.features.base.domain.model.error.Error
import com.features.base.presentation.model.BaseState

data class SplashState(
    override val error: Error? = null
): BaseState(isLoading = false, error = error)