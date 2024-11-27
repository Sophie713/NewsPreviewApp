package com.sophiemiller.newsapp.presentation.ui.composeScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sophiemiller.newsapp.R
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.NewsAppSharedViewModel
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.events.NewsAppEvents
import com.sophiemiller.newsapp.presentation.utils.SingleClickHelperImpl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenArticleDetails(sharedNewsAppViewModel: NewsAppSharedViewModel) {

    val uiState by sharedNewsAppViewModel.articleUiState.collectAsState()
    val singleClickHelper = remember { SingleClickHelperImpl() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = uiState.articleDetails?.sourceName.toString()) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            singleClickHelper.onClick {
                                sharedNewsAppViewModel.onEvent(NewsAppEvents.OnNavigateBack)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            // Share the article
                            singleClickHelper.onClick {
                                sharedNewsAppViewModel.onEvent(NewsAppEvents.OnShareClicked)
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
                    }
                },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
        ) {
            // Article image
            AsyncImage(
                model = uiState.articleDetails?.imageUrl,
                contentDescription = uiState.articleDetails?.title
                    ?: stringResource(R.string.preview_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                placeholder = painterResource(R.drawable.ic_logo),
                error = painterResource(R.drawable.ic_logo)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Article title
            Text(
                text = uiState.articleDetails?.title ?: stringResource(R.string.no_title),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Article content
            Text(
                text = uiState.articleDetails?.description ?: stringResource(R.string.no_article),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}