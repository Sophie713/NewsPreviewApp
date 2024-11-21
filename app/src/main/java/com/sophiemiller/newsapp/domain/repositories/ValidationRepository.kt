package com.sophiemiller.newsapp.domain.repositories

import com.sophiemiller.newsapp.BuildConfig

object ValidationRepository {
    /**
     * mock function to validate username and password
     */
    fun validateLogin(username: String, password: String): Boolean {
        return (username == BuildConfig.STATIC_USERNAME && password == BuildConfig.STATIC_PASSWORD)
    }
}