package com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.sophiemiller.newsapp.data.LOG_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

/**
 * shared viewmodel that handles login and data download
 *
 * @property todo
 */
@HiltViewModel
class NewsAppSharedViewModel  @Inject constructor() :
    ViewModel() {

    /**
     * error handler for coroutines
     */
    private val handler = CoroutineExceptionHandler { _, exception ->
        Log.e(LOG_TAG, exception.message.toString())
    }
}