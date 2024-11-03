package com.sophiemiller.newsapp.presentation.ui.screenStates

import com.sophiemiller.newsapp.data.entities.ArticlePreview

data class NewsListUiState(
    val newsList: MutableList<ArticlePreview?> = mutableListOf()
)