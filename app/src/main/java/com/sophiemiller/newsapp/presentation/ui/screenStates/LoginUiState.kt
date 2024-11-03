package com.sophiemiller.newsapp.presentation.ui.screenStates

data class LoginUiState(
    val username: String = "elonga@elonga.com",//todo xyz empty
    val password: String = "ElongaTheBest",
    val isLoading: Boolean = false,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val showLoginRequiredDialog: Boolean = false
)
