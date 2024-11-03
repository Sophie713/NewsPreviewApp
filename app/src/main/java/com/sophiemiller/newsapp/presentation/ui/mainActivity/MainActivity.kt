package com.sophiemiller.newsapp.presentation.ui.mainActivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.sophiemiller.newsapp.presentation.ui.composeScreens.MainActivityFragments
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.NewsAppSharedViewModel
import com.sophiemiller.newsapp.presentation.ui.themes.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * main startup activity that holds all the available screens
 *
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<NewsAppSharedViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                MainActivityFragments(viewModel)
            }
        }
    }
}