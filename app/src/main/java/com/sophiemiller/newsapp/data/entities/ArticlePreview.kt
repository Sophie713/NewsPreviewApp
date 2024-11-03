package com.sophiemiller.newsapp.data.entities

import com.google.gson.annotations.SerializedName

data class ArticlePreview(

    @SerializedName("title")
    val title: String?,

    @SerializedName("link")
    val link: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("image_url")
    val imageUrl: String?,

    @SerializedName("source_name")
    val sourceName: String?,

    @SerializedName("source_icon")
    val sourceIcon: String?,
)