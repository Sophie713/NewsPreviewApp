package com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sophiemiller.newsapp.data.LOG_TAG
import com.sophiemiller.newsapp.domain.repositories.NewsDataRepository
import com.sophiemiller.newsapp.domain.repositories.ValidationRepository
import com.sophiemiller.newsapp.presentation.ui.mainActivity.navigation.NavManager
import com.sophiemiller.newsapp.presentation.ui.mainActivity.navigation.Screens
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.events.NewsAppEvents
import com.sophiemiller.newsapp.presentation.ui.screenStates.LoginUiState
import com.sophiemiller.newsapp.presentation.ui.screenStates.NewsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * shared viewmodel that handles login and data download
 *
 * @property todo
 */
@HiltViewModel
class NewsAppSharedViewModel @Inject constructor(private val newsDataRepository: NewsDataRepository) :
    ViewModel() {

    private var navManager: NavManager? = null

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    private val _newsUiState = MutableStateFlow(NewsListUiState())
    val newsUiState: StateFlow<NewsListUiState> = _newsUiState

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
                _loginUiState.value = loginUiState.value.copy(
                    isLoading = true
                )
                if (event.name.isNullOrEmpty()) {
                    _loginUiState.value = loginUiState.value.copy(
                        usernameError = "Username cannot be empty",
                        isLoading = false,
                    )
                } else if (event.password.isNullOrEmpty()) {
                    _loginUiState.value = loginUiState.value.copy(
                        passwordError = "Password cannot be empty",
                        isLoading = false,
                    )
                }
                //Attempt login validation
                else {
                    if (ValidationRepository.validateLogin(event.name, event.password)) {
                        viewModelScope.launch(Dispatchers.IO) {
                            val response = newsDataRepository.getMoreNews(successfulLogin = true)
                            val newList = newsUiState.value.newsList
                            response?.body()?.results?.let { newList.addAll(it) }
                            _newsUiState.value = NewsListUiState(
                                newsList = newList
                            )
                            withContext(Dispatchers.Main) {
                                //reset login values
                                _loginUiState.value = LoginUiState()
                                navManager?.navigate(Screens.ScreenNewsPreview)
                            }
                        }
                    } else {
                        _loginUiState.value = loginUiState.value.copy(
                            usernameError = "Password and Username don't match",
                            passwordError = "Password and Username don't match",
                            isLoading = false,
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

            is NewsAppEvents.OnNavigate -> {
                navManager?.navigate(event.destination)
            }

            is NewsAppEvents.OnOpenArticle -> {
                //todo xyz intent to web browser
            }
        }
    }

    fun setNavManager(navManager: NavManager) {
        this.navManager = navManager
    }

    /**
     * error handler for coroutines
     */
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e(LOG_TAG, exception.message.toString())
    }
}