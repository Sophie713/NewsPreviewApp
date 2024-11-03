package com.sophiemiller.newsapp.presentation.ui.screenStates

import com.sophiemiller.newsapp.data.entities.ArticlePreview

/**
 * State for [ScreenNewsPreview]
 *
 * @property newsList - list of downloaded articles
 * @property showErrorDialog - when no data is available or there is an error
 * @property isLoading
 */
data class NewsListUiState(
    val newsList: MutableList<ArticlePreview?> = mutableListOf(),
    val showErrorDialog: Boolean = false,
    val isLoading: Boolean = false,
)