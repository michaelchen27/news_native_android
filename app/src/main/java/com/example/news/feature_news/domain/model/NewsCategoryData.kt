package com.example.news.feature_news.domain.model

import com.example.news.core.base.DiffUtilCallbackItem

data class NewsCategoryData(
    val id: Int,
    val name: String,
) : DiffUtilCallbackItem {
    override fun diff() = "$id"
}

data class NewsSourceData(
    val id: String,
    val name: String,
    val description: String,
) : DiffUtilCallbackItem {
    override fun diff() = id
}