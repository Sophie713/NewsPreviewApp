package com.sophiemiller.newsapp.presentation.utils

import android.util.Log

internal interface SingleClickHelper {
    fun onClick(event: () -> Unit)

    companion object
}

class SingleClickHelperImpl(private val timeout : Long = 1000L) : SingleClickHelper {

    private var clickTimeMillis: Long = 0

    override fun onClick(event: () -> Unit) {
        if (System.currentTimeMillis() - clickTimeMillis >= timeout) {
            clickTimeMillis = System.currentTimeMillis()
            event.invoke()
        }
    }
}