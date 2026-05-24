package com.features.lectures.domain

import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import com.features.lectures.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface LecturesRepository {
    fun getArticles(): Flow<Result<List<Article>, Error>>
    fun getSelectedArticle(): Article?
    fun setSelectedArticle(article: Article)
}