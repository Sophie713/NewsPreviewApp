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
     * triggered when the user skips login - no data shown
     */
    data object OnSkipLoginClicked : NewsAppEvents()

    /**
     * triggered when the user scrolls down to the end of the news list
     */
    data object OnEndOfPageReached : NewsAppEvents()

    /**
     * triggered when the user logs out
     */
    data object OnLogOut : NewsAppEvents()

    /**
     * show or hide loading
     *
     * @property isShown
     */
    data class OnLoading(val isShown: Boolean) : NewsAppEvents()
}