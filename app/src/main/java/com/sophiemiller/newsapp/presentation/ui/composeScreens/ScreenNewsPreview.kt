package com.sophiemiller.newsapp.presentation.ui.composeScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sophiemiller.newsapp.R
import com.sophiemiller.newsapp.data.ERR_UNAUTHORIZED
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.NewsAppSharedViewModel
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.events.NewsAppEvents
import com.sophiemiller.newsapp.presentation.ui.screenStates.mappers.getButtonText
import com.sophiemiller.newsapp.presentation.ui.screenStates.mappers.getMessage
import com.sophiemiller.newsapp.presentation.ui.screenStates.mappers.getOnClickEvent
import com.sophiemiller.newsapp.presentation.ui.views.InfoDialog
import com.sophiemiller.newsapp.presentation.ui.views.NewsCard
import com.sophiemiller.newsapp.presentation.ui.views.OneButtonDialog
import com.sophiemiller.newsapp.presentation.utils.SingleClickHelperImpl
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ScreenNewsPreview(
    sharedNewsAppViewModel: NewsAppSharedViewModel
) {

    val uiState by sharedNewsAppViewModel.newsUiState.collectAsState()
    val listState = rememberLazyListState()
    val singleClickHelper = remember { SingleClickHelperImpl(300L) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        uiState.showErrorDialog?.let {
            OneButtonDialog(
                onDismiss = {
                    singleClickHelper.onClick {
                        sharedNewsAppViewModel.onEvent(
                            NewsAppEvents.OnLoadMoreArticles
                        )
                    }
                },
                onButtonClicked = {
                    singleClickHelper.onClick {
                        sharedNewsAppViewModel.onEvent(
                            it.getOnClickEvent(
                                it.errorCode
                            )
                        )
                    }
                },
                title = stringResource(R.string.error_loading_articles),
                description = stringResource(it.getMessage(it.errorCode)),
                buttonText = stringResource(it.getButtonText(it.errorCode)),
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
            if (uiState.isLoading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
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
}