package com.example.news.feature_news.presentation.activity_main.fragments

import com.example.news.core.base.BaseViewModel
import com.example.news.feature_news.domain.model.NewsCategoryData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor() : BaseViewModel() {
    val categories = listOf(
        NewsCategoryData(0, "business"),
        NewsCategoryData(1, "entertainment"),
        NewsCategoryData(2, "general"),
        NewsCategoryData(3, "health"),
        NewsCategoryData(4, "science"),
        NewsCategoryData(5, "sports"),
        NewsCategoryData(6, "technology"),
    )
}