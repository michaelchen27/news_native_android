package com.example.news.feature_news.presentation.activity_main.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.core.base.BaseFragment
import com.example.news.core.util.safeNavigate
import com.example.news.databinding.FragmentCategoryListBinding
import com.example.news.feature_news.presentation.adapters.CategoryListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryListFragment : BaseFragment<FragmentCategoryListBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoryListBinding
        get() = FragmentCategoryListBinding::inflate

    private val adapter = CategoryListAdapter(::onItemClick)
    private val viewModel by viewModels<CategoryListViewModel>()


    override fun initView() {
        binding.apply {
            rvCategories.layoutManager = LinearLayoutManager(requireContext())
            rvCategories.adapter = adapter
            adapter.submitList(viewModel.categories)
        }

    }

    override fun initData() {

    }

    private fun onItemClick(categoryName: String) {
        findNavController().safeNavigate(
            CategoryListFragmentDirections.actionCategoryListFragmentToSourceListFragment(
                categoryName
            )
        )

    }
}