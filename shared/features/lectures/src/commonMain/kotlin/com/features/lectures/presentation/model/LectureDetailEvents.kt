package com.features.lectures.presentation.model

sealed interface LectureDetailEvents {
    data object OnBackClicked : LectureDetailEvents
}