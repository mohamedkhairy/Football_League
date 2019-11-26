package com.example.footballleague.di


import android.app.Application
import com.example.footballleague.R
import com.example.footballleague.database.dao.Dao
import com.example.footballleague.database.FootballDatabase
import com.example.footballleague.util.Constants.BASE_URL
import com.innovolve.iqraaly.cache.api.LiveDataCallAdapterFactory


import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class AppModule {

    //////////////// database provides ///////////////

    @Singleton
    @Provides
    internal fun databseInstance(app: Application): FootballDatabase =
        FootballDatabase.getInstance(app)

    @Singleton
    @Provides
    internal fun databaseDaoProvides(footballDatabase: FootballDatabase): Dao =
        footballDatabase.footballDao()


    ////////////// Retrofit provides ////////////////

    @Singleton
    @Provides
    internal fun bearerTokenInterceptor(app: Application): Interceptor =
        Interceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("X-Auth-Token", app.getString(R.string.api_key))
                    .build()
            )
        }

    fun loggerInterceptor(): Interceptor {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY
        return logger
    }

    @Singleton
    @Provides
    internal fun okHttpClientProvides(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .addInterceptor(interceptor)
            .addInterceptor(loggerInterceptor())
            .build()


    @Singleton
    @Provides
    internal fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


}
