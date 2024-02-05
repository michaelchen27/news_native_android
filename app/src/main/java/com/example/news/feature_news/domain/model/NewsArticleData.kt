package com.example.news.feature_news.domain.model

import com.example.news.core.base.DiffUtilCallbackItem

data class NewsArticleData(
    val title: String,
    val author: String,
    val description: String,
    val url: String,
) : DiffUtilCallbackItem {
    override fun diff() = "$title/$author/$url"
}