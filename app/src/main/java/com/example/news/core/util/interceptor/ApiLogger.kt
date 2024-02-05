package com.example.news.core.util.interceptor

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class ApiLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val prettyPrintJson =
                    GsonBuilder().setPrettyPrinting().create().toJson(JsonParser().parse(message))
                Timber.d(prettyPrintJson)
            } catch (m: JsonSyntaxException) {
                Timber.d(message)
            }
        } else {
            Timber.d(message)
            return
        }
    }
}