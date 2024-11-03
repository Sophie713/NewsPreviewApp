package com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.sophiemiller.newsapp.data.LOG_TAG
import com.sophiemiller.newsapp.domain.repositories.ValidationRepository
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.events.NewsAppEvents
import com.sophiemiller.newsapp.presentation.ui.screenStates.LoginUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * shared viewmodel that handles login and data download
 *
 * @property todo
 */
@HiltViewModel
class NewsAppSharedViewModel @Inject constructor() :
    ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUIState())
    val loginUiState: StateFlow<LoginUIState> = _loginUiState

    /**
     * implements all [NewsAppEvents]
     */
    fun onEvent(event: NewsAppEvents) {
        when (event) {
            is NewsAppEvents.OnEndOfPageReached -> {
                Log.e(LOG_TAG, "OnEndOfPageReached")
            }

            is NewsAppEvents.OnLoading -> {
                Log.e(LOG_TAG, "OnLoading")
            }

            is NewsAppEvents.OnLogOut -> {
                Log.e(LOG_TAG, "OnLogOut")
            }

            is NewsAppEvents.OnLoginClicked -> {
                //Check for empty values
                if (event.name.isNullOrEmpty()) {
                    _loginUiState.value = loginUiState.value.copy(
                        usernameError = "Username cannot be empty"
                    )
                } else if (event.password.isNullOrEmpty()) {
                    _loginUiState.value = loginUiState.value.copy(
                        passwordError = "Password cannot be empty"
                    )
                }
                //Attempt login validation
                else {
                    if (ValidationRepository.validateLogin(event.name, event.password)) {
                        //todo xyz navigate to list
                    } else {
                        _loginUiState.value = loginUiState.value.copy(
                            usernameError = "Password and Username don't match",
                            passwordError = "Password and Username don't match"
                        )
                    }
                }
            }

            is NewsAppEvents.OnSkipLoginClicked -> {
                Log.e(LOG_TAG, "OnSkipLoginClicked")
            }

            is NewsAppEvents.OnNameChanged -> {
                _loginUiState.value = loginUiState.value.copy(
                    username = event.name,
                    usernameError = null,
                    passwordError = null,
                )
            }

            is NewsAppEvents.OnPasswordChanged -> {
                _loginUiState.value = loginUiState.value.copy(
                    password = event.password,
                    usernameError = null,
                    passwordError = null,
                )
            }
        }
    }

    /**
     * error handler for coroutines
     */
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e(LOG_TAG, exception.message.toString())
    }
}