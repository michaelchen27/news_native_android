package com.example.news.core.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

interface DiffUtilCallbackItem {
    fun diff(): String
}


open class BaseObject<T : DiffUtilCallbackItem> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.diff() == newItem.diff()
    }
}

abstract class BaseMultiViewAdapterPagination<M : DiffUtilCallbackItem> :
    PagingDataAdapter<M, ViewHolder<ViewBinding>>(BaseObject<M>()) {
    protected lateinit var context: Context

    abstract fun binding(holder: ViewBinding, item: M, position: Int)

    abstract fun createBinding(
        viewType: Int, inflater: LayoutInflater, parent: ViewGroup
    ): ViewBinding

    abstract override fun getItemViewType(position: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ViewBinding> {
        context = parent.context

        val inflater = LayoutInflater.from(context)
        val binding = createBinding(viewType, inflater, parent)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<ViewBinding>, position: Int) {
        getItem(position)?.let { binding(holder.binding, it, position) }
    }

}


abstract class BaseMultiViewAdapter<M : DiffUtilCallbackItem> :
    ListAdapter<M, ViewHolder<ViewBinding>>(BaseObject<M>()) {

    protected lateinit var context: Context

    abstract fun binding(holder: ViewBinding, item: M, position: Int)

    abstract fun createBinding(
        viewType: Int, inflater: LayoutInflater, parent: ViewGroup
    ): ViewBinding

    abstract override fun getItemViewType(position: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ViewBinding> {
        context = parent.context

        val inflater = LayoutInflater.from(context)
        val binding = createBinding(viewType, inflater, parent)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<ViewBinding>, position: Int) {
        binding(holder.binding, getItem(position), position)
    }
}


abstract class BaseAdapter<VB : ViewBinding, M : DiffUtilCallbackItem> : BaseMultiViewAdapter<M>() {
    abstract fun bind(holder: VB, item: M, position: Int)

    override fun binding(holder: ViewBinding, item: M, position: Int) {
        bind(holder as VB, item, position)
    }

    override fun getItemViewType(position: Int): Int = 0
}

abstract class BaseAdapterPagination<VB : ViewBinding, M : DiffUtilCallbackItem> : BaseMultiViewAdapterPagination<M>() {
    abstract fun bind(holder: VB, item: M, position: Int)

    override fun binding(holder: ViewBinding, item: M, position: Int) {
        bind(holder as VB, item, position)
    }

    override fun getItemViewType(position: Int): Int = 0
}

class ViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)