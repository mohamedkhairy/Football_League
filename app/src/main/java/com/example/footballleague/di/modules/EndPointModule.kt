package com.example.footballleague.di.modules

import com.example.footballleague.network.EndPoints

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class EndPointModule {

    @Provides
    fun provideApi(retrofit: Retrofit): EndPoints = retrofit.create(EndPoints::class.java)

}
