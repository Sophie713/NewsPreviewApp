package com.sophiemiller.newsapp.presentation.ui.mainActivity.navigation

import com.sophiemiller.newsapp.data.Routes

/**
 * sealed class to include all the screens and simplify adding args
 *
 * @property route - all routes defined in Constants.Routes object
 */
sealed class Screens(val route: String) {
    data object ScreenLogin : Screens(Routes.LOGIN)
    data object ScreenNewsPreview : Screens(Routes.NEWS_PREVIEW)
    data object ScreenArticleDetails: Screens(Routes.ARTICLE_DETAILS)
}