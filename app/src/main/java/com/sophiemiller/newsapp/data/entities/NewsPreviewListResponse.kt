package com.sophiemiller.newsapp.data.entities

import com.google.gson.annotations.SerializedName

data class NewsPreviewListResponse(

    @SerializedName("status")
    val status: String,

    @SerializedName("totalResults")
    val totalResults: Long,

    @SerializedName("results")
    val results: List<ArticlePreview?>?,

    @SerializedName("nextPage")
    val nextPage: Long,
)