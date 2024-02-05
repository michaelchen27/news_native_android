package com.example.news.feature_news.domain.repository

import androidx.paging.PagingData
import com.example.news.feature_news.domain.model.NewsArticleData
import com.example.news.feature_news.domain.model.NewsSourceData
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getSources(
        categoryName: String? = null,
    ): Flow<PagingData<NewsSourceData>>

    fun getArticles(
        sourceId: String? = null,
        query: String? = null,
    ): Flow<PagingData<NewsArticleData>>
}