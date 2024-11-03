package com.sophiemiller.newsapp.domain.repositories

import com.sophiemiller.newsapp.data.STATIC_PASSWORD
import com.sophiemiller.newsapp.data.STATIC_USERNAME

object ValidationRepository {
    /**
     * mock function to validate username and password
     */
    fun validateLogin(username: String, password: String): Boolean {
        return (username == STATIC_USERNAME && password == STATIC_PASSWORD)
    }
}