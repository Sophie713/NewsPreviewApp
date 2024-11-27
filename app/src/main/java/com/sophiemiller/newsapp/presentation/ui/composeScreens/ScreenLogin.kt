package com.sophiemiller.newsapp.presentation.ui.composeScreens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.sophiemiller.newsapp.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.NewsAppSharedViewModel
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.events.NewsAppEvents
import com.sophiemiller.newsapp.presentation.utils.SingleClickHelperImpl

@Composable
fun ScreenLogin(sharedNewsAppViewModel: NewsAppSharedViewModel) {

    val uiState by sharedNewsAppViewModel.loginUiState.collectAsState()
    val singleClickHelper = remember { SingleClickHelperImpl() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo at the top
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo), // Replace with your logo resource
            contentDescription = stringResource(R.string.app_logo),
            modifier = Modifier
                .size(80.dp)
                .padding(top = 24.dp, bottom = 24.dp)
        )

        // Username TextField
        OutlinedTextField(
            value = uiState.username,
            onValueChange = {
                sharedNewsAppViewModel.onEvent(NewsAppEvents.OnNameChanged(it))
            },
            label = { Text(stringResource(R.string.username)) },
            isError = uiState.usernameError != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        if (uiState.usernameError != null) {
            Text(
                text = uiState.usernameError?.let { stringResource(it) } ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Password TextField
        OutlinedTextField(
            value = uiState.password,
            onValueChange = {
                sharedNewsAppViewModel.onEvent(NewsAppEvents.OnPasswordChanged(it))
            },
            label = { Text(stringResource(R.string.password)) },
            isError = uiState.passwordError != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        if (uiState.passwordError != null) {
            Text(
                text = uiState.passwordError?.let { stringResource(it) } ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Main Button
        Button(
            onClick = {
                singleClickHelper.onClick {
                    sharedNewsAppViewModel.onEvent(
                        NewsAppEvents.OnLoginClicked(
                            uiState.username,
                            uiState.password
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(stringResource(R.string.login))
        }

        // Secondary Button
        OutlinedButton(
            onClick = {
                singleClickHelper.onClick { sharedNewsAppViewModel.onEvent(NewsAppEvents.OnLoginSkipped) }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.skip_login))
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


