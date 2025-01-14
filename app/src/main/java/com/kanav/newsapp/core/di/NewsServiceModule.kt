package com.kanav.newsapp.core.di

import com.kanav.newsapp.news.api.NewsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NewsServiceModule {

    private const val BASE_URL = "https://newsapi.org/v2/top-headlines/country=in/"

    @Singleton
    @Provides
    fun provideNewsService(): NewsService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsService::class.java)
    }
}