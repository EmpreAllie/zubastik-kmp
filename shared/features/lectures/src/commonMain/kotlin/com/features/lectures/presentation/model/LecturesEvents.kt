package com.features.lectures.presentation.model

import com.features.lectures.domain.model.Article

sealed interface LecturesEvents {
    data object OnCloseErrorDialog: LecturesEvents
    data class OnArticleClick(val article: Article) : LecturesEvents
}