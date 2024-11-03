package com.sophiemiller.newsapp.domain.repositories

import com.sophiemiller.newsapp.data.entities.NewsPreviewListResponse
import com.sophiemiller.newsapp.domain.inerfaces.NewsDataApi
import retrofit2.Response
import javax.inject.Inject

class NewsDataRepository @Inject constructor(private val api: NewsDataApi) {

    suspend fun getMoreNews(pageNumber: Int): Response<NewsPreviewListResponse?>? {
        return api.getNewsList(pageNumber = pageNumber)
    }
}