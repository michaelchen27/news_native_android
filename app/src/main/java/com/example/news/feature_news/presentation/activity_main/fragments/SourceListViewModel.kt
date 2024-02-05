package com.example.news.feature_news.presentation.activity_main.fragments

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.example.news.core.base.BaseViewModel
import com.example.news.feature_news.domain.model.NewsSourceData
import com.example.news.feature_news.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourceListViewModel @Inject constructor(private val newsRepository: NewsRepository) :
    BaseViewModel() {

    var searchSource: String? = null

    private val _sourcePaging = MutableStateFlow<PagingData<NewsSourceData>?>(null)
    val sourcePaging = _sourcePaging.asStateFlow()

    fun loadSources(categoryName: String) = viewModelScope.launch {
        _sourcePaging.value = newsRepository.getSources(categoryName).map { pagingData ->
            pagingData.map {
                it
            }
        }.cachedIn(viewModelScope).first()
    }

    fun loadFilteredSources(categoryName: String) = viewModelScope.launch {
        _sourcePaging.value = newsRepository.getSources(categoryName).map { pagingData ->
            pagingData.map {
                it
            }
        }.cachedIn(viewModelScope).first().filter {
            searchSource?.let { it1 -> it.name.contains(it1) } == true
        }
    }

    fun clearSource() {
        _sourcePaging.value = PagingData.empty()
    }


}