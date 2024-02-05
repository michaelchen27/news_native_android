package com.example.news.feature_news.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.news.core.ServicesContainer
import com.example.news.core.base.BaseRepository
import com.example.news.feature_news.domain.model.NewsArticleData
import com.example.news.feature_news.domain.model.NewsSourceData
import com.example.news.feature_news.domain.pagination.NewsArticlePagingSource
import com.example.news.feature_news.domain.pagination.NewsSourcePagingSource
import com.example.news.feature_news.domain.repository.NewsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context, private val servicesContainer: ServicesContainer
) : BaseRepository(context), NewsRepository {

    companion object {
        const val NETWORK_PAGE_SIZE = 5
    }

    override fun getSources(
        categoryName: String?,
    ): Flow<PagingData<NewsSourceData>> {

        return Pager(config = PagingConfig(
            initialLoadSize = 5,
            pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false,
            prefetchDistance = 1,
        ), pagingSourceFactory = {
            NewsSourcePagingSource(
                api = servicesContainer.newsAPI, categoryName = categoryName ?: ""
            )
        }).flow

    }

    override fun getArticles(sourceId: String?, query: String?): Flow<PagingData<NewsArticleData>> {
        return Pager(config = PagingConfig(
            initialLoadSize = 5,
            pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false,
            prefetchDistance = 1,

            ), pagingSourceFactory = {
            NewsArticlePagingSource(
                api = servicesContainer.newsAPI, sourceId = sourceId ?: "", query = query
            )
        }).flow
    }
}