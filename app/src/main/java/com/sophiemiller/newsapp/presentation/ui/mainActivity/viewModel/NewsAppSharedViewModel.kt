package com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sophiemiller.newsapp.domain.repositories.NewsDataRepository
import com.sophiemiller.newsapp.domain.repositories.ValidationRepository
import com.sophiemiller.newsapp.presentation.ui.mainActivity.navigation.NavManager
import com.sophiemiller.newsapp.presentation.ui.mainActivity.navigation.Screens
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.events.NewsAppEvents
import com.sophiemiller.newsapp.presentation.ui.screenStates.LoginUiState
import com.sophiemiller.newsapp.presentation.ui.screenStates.NewsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
import com.sophiemiller.newsapp.data.entities.ArticlePreview
import com.sophiemiller.newsapp.data.entities.ListErrorDialog
import com.sophiemiller.newsapp.domain.errorHandler.getCoroutineHandler
import com.sophiemiller.newsapp.presentation.ui.screenStates.ArticleUiState

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

    private val _articleUiState = MutableStateFlow(ArticleUiState())
    val articleUiState: StateFlow<ArticleUiState> = _articleUiState

    /**
     * Next page to load from articles, comes every time articles get downloaded
     */
    private var nextPage: Long? = null

    /**
     * Successful login to enable login skip - no API key in that case
     */
    private var successfulLogin = false

    private val _shareArticle = MutableSharedFlow<ArticlePreview?>()
    val shareArticle = _shareArticle.asSharedFlow()

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
                    // set successful login
                    successfulLogin = true
                    navigateToListOfArticles()
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

            is NewsAppEvents.OnLoginSkipped -> {
                successfulLogin = false
                navigateToListOfArticles()
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
                _articleUiState.value = articleUiState.value.copy(
                    articleDetails = newsUiState.value.newsList[event.position]
                )
                navManager?.navigate(Screens.ScreenArticleDetails)
            }

            is NewsAppEvents.OnShareClicked -> {
                CoroutineScope(Dispatchers.Default).launch {
                    _shareArticle.emit(articleUiState.value.articleDetails)
                }
            }

            is NewsAppEvents.OnNavigateBack -> {
                navManager?.popBackStack()
            }

            is NewsAppEvents.OnLoginShown -> {
               _newsUiState.value = NewsListUiState()
            }
        }
    }

    private fun loadMoreArticles() {
        _newsUiState.value = newsUiState.value.copy(
            showErrorDialog = null,
            isLoading = true
        )
        viewModelScope.launch(Dispatchers.IO + getCoroutineHandler {
            _newsUiState.value = newsUiState.value.copy(
                showErrorDialog = ListErrorDialog(),
                isLoading = false
            )
        }) {
            val response =
                newsDataRepository.getMoreNews(
                    successfulLogin = successfulLogin,
                    pageNumber = nextPage
                )
            if (response?.isSuccessful == true && response.body()?.results != null) {
                nextPage = response.body()?.nextPage
                val newList = newsUiState.value.newsList
                response.body()?.results?.let { newList.addAll(it) }
                _newsUiState.value = newsUiState.value.copy(
                    newsList = newList,
                    isLoading = false
                )
            }
            // error
            else {
                withContext(Dispatchers.Main) {
                    _newsUiState.value = newsUiState.value.copy(
                        showErrorDialog = ListErrorDialog(response?.code()),
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun navigateToListOfArticles() {
        navManager?.navigate(Screens.ScreenNewsPreview)
        _loginUiState.value = LoginUiState()
    }

    fun setNavManager(navManager: NavManager) {
        this.navManager = navManager
    }

}