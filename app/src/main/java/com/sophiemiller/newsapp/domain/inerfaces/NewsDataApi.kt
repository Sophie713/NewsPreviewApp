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
    @GET("api/1/latest")//https://newsdata.io/api/1/latest?apikey=pub_580953b403dc1610a7453c0255835f0664e39&q=YOUR-QUERY&page=XXXPPPXXXXXXXXXX
    suspend fun getNewsList(
        @Query("apikey") apiKey: String,
        @Query("page") pageNumber: Int?
    ): Response<NewsPreviewListResponse?>?
}