package com.sophiemiller.newsapp.presentation.ui.screenStates

import androidx.annotation.StringRes

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    @StringRes
    val usernameError: Int? = null,
    @StringRes
    val passwordError: Int? = null,
)
