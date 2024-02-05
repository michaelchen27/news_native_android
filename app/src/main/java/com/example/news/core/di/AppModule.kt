package com.example.news.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.news.BuildConfig
import com.example.news.core.ServicesContainer
import com.example.news.core.util.Constants
import com.example.news.core.util.interceptor.ApiLogger
import com.example.news.feature_news.data.remote.NewsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideClientHttp(
        @ApplicationContext context: Context,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder().connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)

        if (BuildConfig.DEBUG) {
            val logging =
                HttpLoggingInterceptor(ApiLogger()).setLevel(HttpLoggingInterceptor.Level.BODY)

            builder.addInterceptor(logging).addInterceptor(ChuckerInterceptor(context))
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideApiRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
    }

    @Provides
    @Singleton
    fun provideApi(
        apiRetrofit: Retrofit
    ): ServicesContainer {
        return ServicesContainer(
            newsAPI = apiRetrofit.create(NewsAPI::class.java)
        )


    }
}