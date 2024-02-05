package com.example.news.core.utility_class

import okhttp3.ResponseBody

class SendRequestException(
    message: String?,
    val tag: String = "",
    val code: Int = 0,
    val errMsg: ResponseBody? = null,
) : Exception(message)

class TestException(message: String?) : Exception(message)