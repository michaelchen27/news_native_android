package com.example.news.feature_news.presentation.activity_main.fragments

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.news.core.base.BaseViewModel
import com.example.news.feature_news.domain.model.NewsArticleData
import com.example.news.feature_news.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    BaseViewModel() {

    var searchArticle: String? = null

    private val _articlePaging = MutableStateFlow<PagingData<NewsArticleData>?>(null)
    val articlePaging = _articlePaging.asStateFlow()

    fun loadArticles(sourceId: String) = viewModelScope.launch {
        _articlePaging.value =
            newsRepository.getArticles(sourceId = sourceId, query = searchArticle)
                .map { pagingData ->
                    pagingData.map {
                        it
                    }
                }.cachedIn(viewModelScope).first()
    }
}