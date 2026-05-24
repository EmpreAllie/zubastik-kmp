package com.features.lectures.presentation.model

import com.features.base.domain.model.error.Error
import com.features.base.presentation.model.BaseState
import com.features.lectures.domain.model.Article

data class LecturesState(
    override val isLoading: Boolean = false,
    override val error: Error? = null,

    val articles: List<Article> = emptyList(),
) : BaseState(
    isLoading = isLoading,
    error = error
)
