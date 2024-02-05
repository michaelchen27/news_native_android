package com.example.news.feature_news.data.remote.dto.response

import com.google.gson.annotations.SerializedName


data class SourceResponse(
    @SerializedName("status") var status: String? = null,
    @SerializedName("sources") var sources: ArrayList<SourcesDto> = arrayListOf()
)

data class SourcesDto(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("category") var category: String? = null,
    @SerializedName("language") var language: String? = null,
    @SerializedName("country") var country: String? = null
)