package com.features.lectures.domain.model

data class Article(
    val id: String,
    val authorUuid: String,
    val title: String,
    val content: String,
    val status: String,
    val imageUrl: String,
    val createdAt: String,
)
