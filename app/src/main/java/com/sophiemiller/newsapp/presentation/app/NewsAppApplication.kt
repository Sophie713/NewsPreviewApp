package com.sophiemiller.newsapp.presentation.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * Set application as Hilt Application for DI purposes
 *
 */
@HiltAndroidApp
class NewsAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

}