package com.sophiemiller.newsapp.presentation.ui.screenStates

data class LoginUIState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val userNameError: String? = null,
    val passwordError: String? = null
)
