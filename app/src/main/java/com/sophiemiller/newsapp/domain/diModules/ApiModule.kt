package com.sophiemiller.newsapp.domain.diModules

import com.sophiemiller.newsapp.domain.inerfaces.NewsDataApi
import com.sophiemiller.newsapp.domain.repositories.NewsDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Dagger/Hilt module for domain layer
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideNewsDataApi(retrofit: Retrofit): NewsDataApi {
        return retrofit.create(NewsDataApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsDataRepository(newsDataApi: NewsDataApi): NewsDataRepository {
        return NewsDataRepository(newsDataApi)
    }
}