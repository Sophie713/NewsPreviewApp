package com.sophiemiller.newsapp.data.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * interceptor adding header with an apiKey to communicate with newsdata.io website
 *
 * @property apiKey
 */
class AuthInterceptor(private val apiKey: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val request: Request = original.newBuilder()
            .header("Authorization", apiKey)
            .build()
        return chain.proceed(request)
    }
}