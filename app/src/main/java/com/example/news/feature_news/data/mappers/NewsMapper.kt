package com.example.news.feature_news.data.mappers

import com.example.news.feature_news.data.remote.dto.response.ArticleDto
import com.example.news.feature_news.data.remote.dto.response.SourcesDto
import com.example.news.feature_news.domain.model.NewsArticleData
import com.example.news.feature_news.domain.model.NewsSourceData

fun SourcesDto.toDomain() = NewsSourceData(
    id = id ?: "", name = name ?: "", description = description ?: ""
)

fun List<SourcesDto>.toSourceListDomain() = map {
    it.toDomain()
}

fun ArticleDto.toDomain() = NewsArticleData(
    title = title ?: "",
    author = author ?: "",
    description = description ?: "",
    url = url ?: "",
)

fun List<ArticleDto>.toArticleListDomain() = map {
    it.toDomain()
}