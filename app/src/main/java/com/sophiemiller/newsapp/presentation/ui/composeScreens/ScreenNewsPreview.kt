package com.sophiemiller.newsapp.presentation.ui.composeScreens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.NewsAppSharedViewModel
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.events.NewsAppEvents
import com.sophiemiller.newsapp.presentation.ui.views.InfoDialog
import com.sophiemiller.newsapp.presentation.ui.views.NewsCard
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ScreenNewsPreview(
    sharedNewsAppViewModel: NewsAppSharedViewModel
) {

    val uiState by sharedNewsAppViewModel.newsUiState.collectAsState()
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.showErrorDialog) {
            InfoDialog(
                onDismiss = { sharedNewsAppViewModel.onEvent(NewsAppEvents.OnLoadMoreArticles) },
                title = "Error loading articles",
                description = "You need to log in to see articles. Please log in to continue."
            )
        }
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiState.newsList) { article ->
                article?.let {
                    NewsCard(
                        articlePreview = it,
                        onArticleClicked = { url ->
                            sharedNewsAppViewModel.onEvent(NewsAppEvents.OnArticleClicked(url))

                        })
                }
            }
        }
        LaunchedEffect(listState) {
            snapshotFlow { listState.firstVisibleItemIndex + listState.layoutInfo.visibleItemsInfo.size >= listState.layoutInfo.totalItemsCount }
                .distinctUntilChanged()
                .collect { isAtEnd ->
                    if (isAtEnd) {
                        sharedNewsAppViewModel.onEvent(NewsAppEvents.OnLoadMoreArticles)
                    }
                }
        }
    }
    // Loading overlay
    if (uiState.isLoading) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Gray.copy(alpha = 0.5f))
                .clickable { }
        )

        CircularProgressIndicator(
            Modifier
                .fillMaxWidth()
                .padding(40.dp)
        )
    }
}