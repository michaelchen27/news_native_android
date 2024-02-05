package com.example.news.feature_news.domain.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.news.feature_news.data.mappers.toArticleListDomain
import com.example.news.feature_news.data.remote.NewsAPI
import com.example.news.feature_news.domain.model.NewsArticleData
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException


class NewsArticlePagingSource(private val api: NewsAPI,
                              private val sourceId: String,
                              private val query: String?,
) :
    PagingSource<Int, NewsArticleData>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, NewsArticleData>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsArticleData> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response =
                api.getArticles(sources = sourceId, pageSize = params.loadSize, page = page, query = query)

            val data = response.body()?.articles

            LoadResult.Page(
                data = data?.toArticleListDomain() ?: emptyList(),
                prevKey = null,
                nextKey = if (data?.toArticleListDomain()?.isEmpty() == true) null else page + 1,
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: IllegalStateException) {
            return LoadResult.Error(e)

        } catch (e: Exception) {
            Timber.e(e)
            return LoadResult.Error(e)
        }

    }
}