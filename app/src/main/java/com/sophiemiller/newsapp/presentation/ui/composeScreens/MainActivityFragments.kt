package com.sophiemiller.newsapp.presentation.ui.composeScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sophiemiller.newsapp.presentation.ui.mainActivity.navigation.NavManager
import com.sophiemiller.newsapp.presentation.ui.mainActivity.navigation.Screens
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.NewsAppSharedViewModel

@Composable
fun MainActivityFragments(sharedNewsAppViewModel: NewsAppSharedViewModel) {

    /**
     * edge to edge padding
     */
    Box(modifier = Modifier
        .safeDrawingPadding()
        .verticalScroll(rememberScrollState())) {

        val navController = rememberNavController()
        val navManager = NavManager(navController)
        sharedNewsAppViewModel.setNavManager(navManager)

        NavHost(navController, startDestination = Screens.ScreenLogin.route) {
            /**
             * Screen that handles Login
             */
            composable(Screens.ScreenLogin.route) {
                ScreenLogin(
                    sharedNewsAppViewModel = sharedNewsAppViewModel
                )
            }

            /**
             * Screen that shows list of news
             */
            composable(Screens.ScreenNewsPreview.route) {
                ScreenNewsPreview(
                    sharedNewsAppViewModel = sharedNewsAppViewModel
                )
            }

            /**
             * Screen that shows no data
             */
            composable(Screens.NoDataScreen.route) {
                ScreenNoData(
                    sharedNewsAppViewModel = sharedNewsAppViewModel
                )
            }
        }
    }
}
