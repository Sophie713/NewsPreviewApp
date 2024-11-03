package com.sophiemiller.newsapp.domain.repositories

import com.sophiemiller.newsapp.BuildConfig
import com.sophiemiller.newsapp.data.entities.NewsPreviewListResponse
import com.sophiemiller.newsapp.domain.inerfaces.NewsDataApi
import retrofit2.Response
import javax.inject.Inject

class NewsDataRepository @Inject constructor(private val api: NewsDataApi) {

    /**
     * get more articles to preview - only loading english articles
     *
     * @param pageNumber
     * @param successfulLogin - only add apiKey when login is successful
     * @return
     */
    suspend fun getMoreNews(
        pageNumber: Long? = null,
        successfulLogin: Boolean
    ): Response<NewsPreviewListResponse?>? {
        return api.getNewsList(
            pageNumber = pageNumber,
            apiKey = if (successfulLogin) BuildConfig.NEWS_API_KEY else "",
            language = "en"
        )
    }
}