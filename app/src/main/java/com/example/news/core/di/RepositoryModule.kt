package com.example.news.core.di

import com.example.news.feature_news.data.repository.NewsRepositoryImpl
import com.example.news.feature_news.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(
        newsRepository: NewsRepositoryImpl
    ): NewsRepository
}