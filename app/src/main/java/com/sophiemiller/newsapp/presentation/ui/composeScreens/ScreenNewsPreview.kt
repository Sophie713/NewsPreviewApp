package com.sophiemiller.newsapp.presentation.ui.composeScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.NewsAppSharedViewModel
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.events.NewsAppEvents
import com.sophiemiller.newsapp.presentation.ui.views.NewsCard

@Composable
fun ScreenNewsPreview(
    sharedNewsAppViewModel: NewsAppSharedViewModel
) {

    val uiState by sharedNewsAppViewModel.newsUiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        uiState.newsList.forEach { article ->
            article?.let {
                NewsCard(
                    articlePreview = it,
                    onArticleClicked = { url -> sharedNewsAppViewModel.onEvent(NewsAppEvents.OnOpenArticle(url)) })
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}