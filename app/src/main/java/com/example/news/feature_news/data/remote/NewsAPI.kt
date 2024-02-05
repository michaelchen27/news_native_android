package com.example.news.feature_news.data.remote

import com.example.news.core.util.Constants
import com.example.news.feature_news.data.remote.dto.response.ArticleResponse
import com.example.news.feature_news.data.remote.dto.response.SourceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("/v2/top-headlines/sources")
    suspend fun getSources(
        @Query("apiKey") apiKey: String? = Constants.API_KEY,
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int?,
        @Query("page") page: Int?,
        @Query("language") language: String? = "en",
    ): Response<SourceResponse>

    @GET("/v2/everything")
    suspend fun getArticles(
        @Query("apiKey") apiKey: String? = Constants.API_KEY,
        @Query("q") query: String? = null,
        @Query("searchIn") searchIn: String? = "title",
        @Query("sources") sources: String,
        @Query("pageSize") pageSize: Int?,
        @Query("page") page: Int?,
    ): Response<ArticleResponse>

}