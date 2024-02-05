package com.example.news.core.utility_class

import com.google.gson.annotations.SerializedName

data class GenericResponse<T>(
    @SerializedName("data") val data: T,

    @SerializedName("message") val message: String,

    @SerializedName("status_code") val statusCode: Int,

    )