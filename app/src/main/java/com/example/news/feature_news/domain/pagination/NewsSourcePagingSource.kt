package com.example.news.feature_news.domain.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.news.feature_news.data.mappers.toArticleListDomain
import com.example.news.feature_news.data.mappers.toSourceListDomain
import com.example.news.feature_news.data.remote.NewsAPI
import com.example.news.feature_news.domain.model.NewsSourceData
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException


class NewsSourcePagingSource(private val api: NewsAPI, private val categoryName: String) :
    PagingSource<Int, NewsSourceData>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, NewsSourceData>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsSourceData> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response =
                api.getSources(category = categoryName, pageSize = params.loadSize, page = page)

            val data = response.body()?.sources

            LoadResult.Page(
                data = data?.toSourceListDomain() ?: emptyList(),
                prevKey = null,
                nextKey = if (data?.toSourceListDomain()?.isEmpty() == true) null else page + 1,
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