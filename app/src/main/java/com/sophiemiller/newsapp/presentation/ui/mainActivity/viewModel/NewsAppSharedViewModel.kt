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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.sophiemiller.newsapp.R

/**
 * shared viewmodel that handles login and news
 *
 * @property [NewsDataRepository] repo to download articles
 */
@HiltViewModel
class NewsAppSharedViewModel @Inject constructor(private val newsDataRepository: NewsDataRepository) :
    ViewModel() {

    private var navManager: NavManager? = null

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    private val _newsUiState = MutableStateFlow(NewsListUiState())
    val newsUiState: StateFlow<NewsListUiState> = _newsUiState

    private var nextPage: Long? = null

    private val _openUrlEvent = MutableSharedFlow<String?>()
    val openUrlEvent = _openUrlEvent.asSharedFlow()

    /**
     * implements all [NewsAppEvents]
     */
    fun onEvent(event: NewsAppEvents) {
        when (event) {
            is NewsAppEvents.OnLoginClicked -> {
                //Check for empty values
                _loginUiState.value = loginUiState.value.copy(
                    isLoading = true
                )
                if (event.name.isEmpty()) {
                    _loginUiState.value = loginUiState.value.copy(
                        usernameError = R.string.username_cannot_be_empty,
                        isLoading = false,
                    )
                } else if (event.password.isEmpty()) {
                    _loginUiState.value = loginUiState.value.copy(
                        passwordError = R.string.password_cannot_be_empty,
                        isLoading = false,
                    )
                }
                //Attempt login validation
                else if (ValidationRepository.validateLogin(event.name, event.password)) {
                    //reset login values
                    _loginUiState.value = LoginUiState()
                    navManager?.navigate(Screens.ScreenNewsPreview)
                    loadMoreArticles()
                }
                // not empty nor valid
                else {
                    _loginUiState.value = loginUiState.value.copy(
                        usernameError = R.string.password_and_username_don_t_match,
                        passwordError = R.string.password_and_username_don_t_match,
                        isLoading = false,
                    )
                }
            }

            is NewsAppEvents.OnLoginDialogShow -> {
                _loginUiState.value = loginUiState.value.copy(
                    showLoginRequiredDialog = event.isShow
                )
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

            is NewsAppEvents.OnLoadMoreArticles -> {
                loadMoreArticles()
            }

            is NewsAppEvents.OnArticleClicked -> {
                CoroutineScope(Dispatchers.Default).launch {
                    _openUrlEvent.emit(event.url)
                }
            }
        }
    }

    private fun loadMoreArticles() {
        _newsUiState.value = newsUiState.value.copy(
            showErrorDialog = false,
            isLoading = newsUiState.value.newsList.isEmpty()
        )
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
            _newsUiState.value = newsUiState.value.copy(
                showErrorDialog = true,
                isLoading = false
            )
            logCoroutineException(throwable)
        }) {
            val response =
                newsDataRepository.getMoreNews(successfulLogin = true, pageNumber = nextPage)
            if (response?.isSuccessful == true && response.body()?.results != null) {
                nextPage = response.body()?.nextPage
                val newList = newsUiState.value.newsList
                response.body()?.results?.let { newList.addAll(it) }
                _newsUiState.value = NewsListUiState(
                    newsList = newList
                )
            }
            // error
            else {
                withContext(Dispatchers.Main) {
                    _newsUiState.value = newsUiState.value.copy(
                        showErrorDialog = true,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun setNavManager(navManager: NavManager) {
        this.navManager = navManager
    }

    /**
     * error logger for coroutines
     */
    private fun logCoroutineException(exception: Throwable) {
        Log.e(LOG_TAG, exception.message.toString())
    }
}