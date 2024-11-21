package com.sophiemiller.newsapp.presentation.ui.screenStates.mappers

import androidx.annotation.StringRes
import com.sophiemiller.newsapp.R
import com.sophiemiller.newsapp.data.ERR_UNAUTHORIZED
import com.sophiemiller.newsapp.data.entities.ListErrorDialog
import com.sophiemiller.newsapp.presentation.ui.mainActivity.viewModel.events.NewsAppEvents

@StringRes
fun ListErrorDialog.getMessage(errorCode: Int? = null) : Int {
    return when (errorCode) {
        ERR_UNAUTHORIZED -> R.string.error_msg_you_need_to_log_in
        else -> R.string.default_error_msg
    }
}

@StringRes
fun ListErrorDialog.getButtonText(errorCode: Int? = null) : Int {
    return when (errorCode) {
        ERR_UNAUTHORIZED -> R.string.log_in
        else -> R.string.ok
    }
}

fun ListErrorDialog.getOnClickEvent(errorCode: Int? = null) : NewsAppEvents {
    return when (errorCode) {
        ERR_UNAUTHORIZED -> NewsAppEvents.OnNavigateBack
        else -> NewsAppEvents.OnLoadMoreArticles
    }
}