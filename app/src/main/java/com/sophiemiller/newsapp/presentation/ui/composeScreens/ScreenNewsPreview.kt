package com.sophiemiller.newsapp.presentation.ui.composeScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sophiemiller.newsapp.R
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
                title = stringResource(R.string.error_loading_articles),
                description = stringResource(R.string.you_need_to_log_in_to_see_articles_please_log_in_to_continue)
            )
        }
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(uiState.newsList) { index, article ->
                article?.let {
                    NewsCard(
                        articlePreview = it,
                        onArticleClicked = {
                            sharedNewsAppViewModel.onEvent(NewsAppEvents.OnArticleClicked(index))

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