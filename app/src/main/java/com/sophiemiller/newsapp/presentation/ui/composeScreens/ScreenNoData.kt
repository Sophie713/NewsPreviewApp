package com.sophiemiller.newsapp.presentation.ui.composeScreens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.sophiemiller.newsapp.presentation.ui.mainActivity.navigation.Screens
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.NewsAppSharedViewModel
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.events.NewsAppEvents

@Composable
fun ScreenNoData(
    sharedNewsAppViewModel: NewsAppSharedViewModel
) {
    Text(
        modifier = Modifier.clickable {
            sharedNewsAppViewModel.onEvent(
                NewsAppEvents.OnNavigate(
                    Screens.ScreenLogin
                )
            )
        },
        text = "No Data Preview"
    )
}