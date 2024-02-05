package com.example.news.core.utility_class

import okhttp3.ResponseBody

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()

    class Error(
        val message: String?,
        val errBody: ResponseBody?,
        val code: Int = 0,
        val tag: String = "",
    ) : Resource<Nothing>()
}