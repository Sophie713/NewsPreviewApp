package com.sophiemiller.newsapp.presentation.ui.screenStates

data class LoginUIState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val usernameError: String? = null,
    val passwordError: String? = null
)
