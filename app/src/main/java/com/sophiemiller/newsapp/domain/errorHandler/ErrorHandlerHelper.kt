package com.sophiemiller.newsapp.domain.errorHandler

import android.util.Log
import com.sophiemiller.newsapp.data.LOG_TAG
import kotlinx.coroutines.CoroutineExceptionHandler

fun getCoroutineHandler(handleError: () -> Unit): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, throwable ->
        logCoroutineException(throwable)
        handleError.invoke()
    }
}


/**
 * default error logger for all coroutines
 */
private fun logCoroutineException(exception: Throwable) {
    Log.e(LOG_TAG, exception.message.toString())
}
