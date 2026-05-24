package com.features.lectures.presentation

import androidx.lifecycle.viewModelScope
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import com.features.base.presentation.model.BaseViewModel
import com.features.lectures.domain.LecturesRepository
import com.features.lectures.presentation.model.LecturesEffects
import com.features.lectures.presentation.model.LecturesEvents
import com.features.lectures.presentation.model.LecturesState
import kotlinx.coroutines.launch

class LecturesViewModel(
    private val repository: LecturesRepository,
) : BaseViewModel<LecturesState, LecturesEvents, LecturesEffects>(LecturesState()) {

    init { loadArticles() }

    override fun onEvent(event: LecturesEvents) {
        when (event) {
            LecturesEvents.OnCloseErrorDialog -> updateState { it.copy(error = null) }
            is LecturesEvents.OnArticleClick -> {
                repository.setSelectedArticle(event.article)
                sendEffect(LecturesEffects.NavigateToDetail)
            }
        }
    }

    private fun loadArticles() = viewModelScope.launch {
        repository.getArticles().collect { result ->
            when (result) {

                Result.Loading -> updateState {
                    it.copy(isLoading = true)
                }

                is Result.Success -> updateState {
                    it.copy(
                        isLoading = false,
                        articles = result.data
                    )
                }

                is Result.Failure -> updateState {
                    it.copy(
                        isLoading = false,
                        error = result.error
                    )
                }

                Result.ConnectionError -> updateState {
                    it.copy(
                        isLoading = false,
                        error = Error.CONNECTION
                    )
                }

                else -> {}
            }
        }
    }
}