package com.example.news.feature_news.presentation.activity_main.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.news.core.base.BaseFragment
import com.example.news.databinding.FragmentWebViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : BaseFragment<FragmentWebViewBinding>() {


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWebViewBinding
        get() = FragmentWebViewBinding::inflate

    private val args by navArgs<WebViewFragmentArgs>()


    override fun initView() {
        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?, request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return true
            }
        }
        binding.webview.settings.javaScriptEnabled = true

        binding.webview.loadUrl(args.url)

    }

    override fun initData() {

    }

}