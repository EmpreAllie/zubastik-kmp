package com.features.lectures.presentation.model

import com.features.lectures.domain.model.Article

sealed interface LecturesEffects {
    data object NavigateToDetail : LecturesEffects
}