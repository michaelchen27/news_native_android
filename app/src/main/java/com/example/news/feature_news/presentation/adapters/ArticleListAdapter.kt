package com.example.news.feature_news.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.news.core.base.BaseAdapterPagination
import com.example.news.databinding.ItemArticleBinding
import com.example.news.feature_news.domain.model.NewsArticleData

class ArticleListAdapter(private val onItemClick: (String) -> Unit) :
    BaseAdapterPagination<ItemArticleBinding, NewsArticleData>() {

    override fun bind(holder: ItemArticleBinding, item: NewsArticleData, position: Int) =
        with(holder) {
            tvName.text = item.title
            tvAuthor.text = item.author
            tvDesc.text = item.description

            root.setOnClickListener {
                onItemClick.invoke(item.url)
            }
        }

    override fun createBinding(
        viewType: Int, inflater: LayoutInflater, parent: ViewGroup
    ) = ItemArticleBinding.inflate(inflater, parent, false)
}