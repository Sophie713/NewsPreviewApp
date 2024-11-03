package com.sophiemiller.newsapp.presentation.ui.composeScreens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.navigation.NavHostController
import com.sophiemiller.newsapp.R
import androidx.compose.runtime.getValue
import com.sophiemiller.newsapp.data.LOG_TAG
import com.sophiemiller.newsapp.presentation.ui.mainActivity.navigation.Screens
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.NewsAppSharedViewModel
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.events.NewsAppEvents
import com.sophiemiller.newsapp.presentation.ui.views.InfoDialog

@Composable
fun ScreenLogin(sharedNewsAppViewModel: NewsAppSharedViewModel) {

    val uiState by sharedNewsAppViewModel.loginUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.showLoginRequiredDialog) {
            InfoDialog(
                onDismiss = { sharedNewsAppViewModel.onEvent(NewsAppEvents.OnLoginDialogShow(false)) },
                title = "Login Required",
                description = "You need to log in to see articles. Please log in to continue."
            )
        }
        // Logo at the top
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_logo), // Replace with your logo resource
            contentDescription = "App Logo",
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
            label = { Text("Username") },
            isError = uiState.usernameError != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        if (uiState.usernameError != null) {
            Text(
                text = uiState.usernameError ?: "",
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
            label = { Text("Password") },
            isError = uiState.passwordError != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        if (uiState.passwordError != null) {
            Text(
                text = uiState.passwordError ?: "",
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
                sharedNewsAppViewModel.onEvent(
                    NewsAppEvents.OnLoginClicked(
                        uiState.username,
                        uiState.password
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Login")
        }

        // Secondary Button
        OutlinedButton(
            onClick = { sharedNewsAppViewModel.onEvent(NewsAppEvents.OnLoginDialogShow(true)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Skip Login")
        }
    }
}


