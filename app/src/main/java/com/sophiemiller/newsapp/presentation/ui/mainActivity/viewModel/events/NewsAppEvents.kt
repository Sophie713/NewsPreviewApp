package com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.events

import com.sophiemiller.newsapp.presentation.ui.mainActivity.navigation.Screens

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
     * triggered when the user skips login (true) or dismisses the dialog (false)
     */
    data class OnLoginDialogShow(val isShow : Boolean) : NewsAppEvents()

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

    /**
     * navigate to another screen
     *
     * @param destination
     */
    data class OnNavigate(val destination: Screens) : NewsAppEvents()

    /**
     * open the article
     */
    data class OnOpenArticle(val articleUrlLink: String?): NewsAppEvents()
}