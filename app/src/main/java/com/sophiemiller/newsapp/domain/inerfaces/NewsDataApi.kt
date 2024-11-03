package com.sophiemiller.newsapp.domain.inerfaces

import com.sophiemiller.newsapp.data.entities.NewsPreviewListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * api interface for Retrofit
 *
 */
interface NewsDataApi {
    @GET("api/1/latest")
    suspend fun getNewsList(
        @Query("apikey") apiKey: String,
        @Query("page") pageNumber: Long?,
        @Query("language") language: String
    ): Response<NewsPreviewListResponse?>?
}