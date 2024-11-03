package com.sophiemiller.newsapp.presentation.ui.composeScreens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.sophiemiller.newsapp.presentation.ui.mainActivity.navigation.Screens
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.NewsAppSharedViewModel

@Composable
fun ScreenNewsPreview(
    sharedNewsAppViewModel: NewsAppSharedViewModel,
    navController: NavHostController
) {
    Text(
        modifier = Modifier.clickable { navController.navigate(Screens.ScreenLogin.route) },
        text = "Screen News Preview"
    )

}