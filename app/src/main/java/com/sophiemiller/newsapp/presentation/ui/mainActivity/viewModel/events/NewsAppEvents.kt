package com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.events

sealed class NewsAppEvents {

    /**
     * triggered when the user edits name
     *
     * @property name
     */
    data class OnNameChanged(val name: String) : NewsAppEvents()

    /**
     * triggered when the user edits password
     *
     * @property password
     */
    data class OnPasswordChanged(val password: String) : NewsAppEvents()

    /**
     * triggered when the user attempts to login
     *
     * @property name
     * @property password
     */
    data class OnLoginClicked(val name: String, val password: String) : NewsAppEvents()

    /**
     * triggered when the user skips login
     */
    data object OnLoginSkipped : NewsAppEvents()

    /**
     * load more articles request
     */
    data object OnLoadMoreArticles : NewsAppEvents()

    /**
     * on article clicked - open browser
     *
     * @property url
     */
    data class OnArticleClicked(val position: Int) : NewsAppEvents()

    /**
     * triggered when user tries to share an article from the detail
     */
    data object OnShareClicked : NewsAppEvents()

    /**
     * navigate back
     */
    data object OnNavigateBack : NewsAppEvents()

    /**
     * On login screen shown I want to clear all loaded data
     */
    data object OnLoginShown : NewsAppEvents()
}