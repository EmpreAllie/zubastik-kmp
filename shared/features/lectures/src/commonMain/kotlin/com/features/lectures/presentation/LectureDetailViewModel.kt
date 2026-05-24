package com.features.lectures.presentation

import com.features.base.presentation.model.BaseViewModel
import com.features.lectures.domain.LecturesRepository
import com.features.lectures.presentation.model.LectureDetailEffects
import com.features.lectures.presentation.model.LectureDetailEvents
import com.features.lectures.presentation.model.LectureDetailState

class LectureDetailViewModel(
    private val repository: LecturesRepository,
) : BaseViewModel<LectureDetailState, LectureDetailEvents, LectureDetailEffects>(
    LectureDetailState(article = repository.getSelectedArticle())
) {

    override fun onEvent(event: LectureDetailEvents) {
        when (event) {
            LectureDetailEvents.OnBackClicked -> sendEffect(LectureDetailEffects.NavigateBack)
        }
    }
}