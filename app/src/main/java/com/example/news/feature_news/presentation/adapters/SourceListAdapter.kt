package com.example.news.feature_news.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.news.core.base.BaseAdapterPagination
import com.example.news.core.util.toTitleCase
import com.example.news.databinding.ItemSourceBinding
import com.example.news.feature_news.domain.model.NewsSourceData

class SourceListAdapter(private val onItemClick: (String) -> Unit) :
    BaseAdapterPagination<ItemSourceBinding, NewsSourceData>() {

    override fun bind(holder: ItemSourceBinding, item: NewsSourceData, position: Int) =
        with(holder) {
            tvName.text = item.name.toTitleCase()
            tvDesc.text = item.description

            root.setOnClickListener {
                onItemClick.invoke(item.id)
            }
        }

    override fun createBinding(
        viewType: Int, inflater: LayoutInflater, parent: ViewGroup
    ) = ItemSourceBinding.inflate(inflater, parent, false)
}