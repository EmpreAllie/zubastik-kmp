package com.features.lectures.data

import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import com.features.lectures.domain.LecturesRepository
import com.features.lectures.domain.model.Article
import com.network.api.apis.LecturesApi
import com.network.data.exception.isConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.time.ExperimentalTime

class LecturesRepositoryImpl(
    private val lecturesApi: LecturesApi,
) : LecturesRepository {

    private var selectedArticle: Article? = null

    override fun getSelectedArticle(): Article? = selectedArticle

    override fun setSelectedArticle(article: Article) {
        selectedArticle = article
    }

    @OptIn(ExperimentalTime::class)
    override fun getArticles(): Flow<Result<List<Article>, Error>> = flow {
        emit(Result.Loading)
        try {
            val response = lecturesApi.apiV1LecturesGet().body()
            val articles = response.map { dto ->
                Article(
                    id = dto.ID.orEmpty(),
                    authorUuid = dto.authorUUID.orEmpty(),
                    title = dto.title.orEmpty(),
                    content = dto.content.orEmpty(),
                    status = dto.status?.value.orEmpty(),
                    imageUrl = dto.imageURL.orEmpty(),
                    createdAt = dto.createdAt.orEmpty(),
                )
            }
            emit(Result.Success(articles))
        } catch (e: Exception) {
            if (e.isConnectionException()) {
                emit(Result.ConnectionError)
            } else {
                emit(Result.Failure(Error.OTHER(e.message.orEmpty())))
            }
        }
    }.flowOn(Dispatchers.IO)
}