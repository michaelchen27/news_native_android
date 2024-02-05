package com.example.news.feature_news.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.news.core.base.BaseAdapter
import com.example.news.core.util.toTitleCase
import com.example.news.databinding.ItemCategoryBinding
import com.example.news.feature_news.domain.model.NewsCategoryData

class CategoryListAdapter(private val onItemClick: (String) -> Unit) :
    BaseAdapter<ItemCategoryBinding, NewsCategoryData>() {

    override fun bind(holder: ItemCategoryBinding, item: NewsCategoryData, position: Int) =
        with(holder) {
            tvName.text = item.name.toTitleCase()

            root.setOnClickListener {
                onItemClick.invoke(item.name)
            }
        }

    override fun createBinding(
        viewType: Int, inflater: LayoutInflater, parent: ViewGroup
    ) = ItemCategoryBinding.inflate(inflater, parent, false)
}