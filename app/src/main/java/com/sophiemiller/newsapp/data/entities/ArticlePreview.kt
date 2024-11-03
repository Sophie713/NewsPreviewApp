package com.sophiemiller.newsapp.data.entities

import com.google.gson.annotations.SerializedName

data class ArticlePreview(

    @SerializedName("title")
    val title: String,

    @SerializedName("link")
    val link: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("image_url")
    val image_url: String,

    @SerializedName("source_name")
    val source_name: String,

    @SerializedName("source_icon")
    val source_icon: String,
)