package com.features.lectures.presentation.model

sealed interface LectureDetailEffects {
    data object NavigateBack : LectureDetailEffects
}