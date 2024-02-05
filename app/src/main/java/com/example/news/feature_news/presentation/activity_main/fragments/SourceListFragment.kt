package com.example.news.feature_news.presentation.activity_main.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.core.base.BaseFragment
import com.example.news.core.enumerates.ViewState
import com.example.news.core.util.onSearch
import com.example.news.core.util.safeNavigate
import com.example.news.databinding.FragmentSourceListBinding
import com.example.news.feature_news.presentation.adapters.SourceListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SourceListFragment : BaseFragment<FragmentSourceListBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSourceListBinding
        get() = FragmentSourceListBinding::inflate

    private val adapter = SourceListAdapter(::onItemClick)
    private val viewModel by viewModels<SourceListViewModel>()
    private val args by navArgs<SourceListFragmentArgs>()

    override fun initView() {
        binding.apply {
            infoCard.tvInfo.text = "List Source dari API terus berulang meskipun page di-increment."

            rvSources.layoutManager = LinearLayoutManager(requireContext())
            rvSources.adapter = adapter

            etSearch.onSearch().debounce(500).onEach { keyword ->
                viewModel.searchSource = keyword
                viewModel.clearSource()

                if (keyword.isEmpty()) {
                    viewModel.loadSources(args.categoryName)
                } else {

                    viewModel.loadFilteredSources(args.categoryName)
                }

            }.launchIn(lifecycleScope)
        }
    }

    private fun onStateChange(state: ViewState) {
        when (state) {
            ViewState.LOADING -> {
                binding.loading.isVisible = true
            }

            ViewState.SUCCESS, ViewState.ERROR -> {
                binding.loading.isVisible = false
            }

        }
    }

    override fun initData() {
        viewModel.state.observe(viewLifecycleOwner, ::onStateChange)

        lifecycleScope.launch {
            launch {
                adapter.loadStateFlow.collectLatest { loadState ->
                    viewModel.apply {
                        if (loadState.source.refresh is LoadState.Loading) {
                            viewModel.changeUIViewState(ViewState.LOADING)

                        } else if (loadState.source.refresh is LoadState.NotLoading && adapter.itemCount < 1) {
                            viewModel.changeUIViewState(ViewState.ERROR)

                        } else if (loadState.source.refresh is LoadState.NotLoading && adapter.itemCount > 0) {
                            viewModel.changeUIViewState(ViewState.SUCCESS)
                        }
                    }
                }
            }

            launch {
                viewModel.sourcePaging.collectLatest {
                    if (it != null) {
                        adapter.submitData(it)
                    }
                }
            }
        }

        viewModel.loadSources(categoryName = args.categoryName)

    }

    private fun onItemClick(sourceId: String) {
        findNavController().safeNavigate(
            SourceListFragmentDirections.actionSourceListFragmentToArticleListFragment(
                sourceId
            )
        )

    }


}