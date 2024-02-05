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
import com.example.news.databinding.FragmentArticleListBinding
import com.example.news.feature_news.presentation.adapters.ArticleListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleListFragment : BaseFragment<FragmentArticleListBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentArticleListBinding
        get() = FragmentArticleListBinding::inflate


    private val adapter = ArticleListAdapter(::onItemClick)
    private val viewModel by viewModels<ArticleListViewModel>()
    private val args by navArgs<ArticleListFragmentArgs>()

    override fun initView() {
        binding.apply {
            rvArticles.layoutManager = LinearLayoutManager(requireContext())
            rvArticles.adapter = adapter

            etSearch.onSearch().debounce(500).onEach { keyword ->
                viewModel.searchArticle = keyword.ifEmpty { null }
                viewModel.loadArticles(args.sourceId)

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
                viewModel.articlePaging.collectLatest {
                    if (it != null) {
                        adapter.submitData(it)
                    }
                }
            }
        }

        viewModel.loadArticles(sourceId = args.sourceId)

    }

    private fun onItemClick(url: String) {
        findNavController().safeNavigate(
            ArticleListFragmentDirections.actionArticleListFragmentToWebViewFragment(
                url
            )
        )
    }


}