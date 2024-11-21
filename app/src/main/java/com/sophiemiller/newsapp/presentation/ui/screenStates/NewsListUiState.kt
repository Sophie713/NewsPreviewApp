package com.sophiemiller.newsapp.presentation.ui.screenStates

import com.sophiemiller.newsapp.data.entities.ArticlePreview
import com.sophiemiller.newsapp.data.entities.ListErrorDialog

/**
 * State for [ScreenNewsPreview]
 *
 * @property newsList - list of downloaded articles
 * @property showErrorDialog - when no data is available or there is an error
 * @property isLoading
 */
data class NewsListUiState(
    val newsList: MutableList<ArticlePreview?> = mutableListOf(),
    val showErrorDialog: ListErrorDialog? = null,
    val isLoading: Boolean = false,
)