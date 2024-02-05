package com.example.news.core.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<B : ViewBinding> : Fragment() {
    protected lateinit var binding: B
        private set

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initCreate()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initData()

        bind(binding)
    }

    abstract fun initView()
    abstract fun initData()

    open fun bind(binding: B) {}
    open fun initCreate() {}


    fun Activity.dismissKeyboard() {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputMethodManager.isAcceptingText) inputMethodManager.hideSoftInputFromWindow(
            this.currentFocus?.windowToken, /*flags:*/
            0
        )
    }

}