package com.example.myapplication.di

import com.example.myapplication.retrofit.Api
import com.example.myapplication.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    //creates the retrofit object
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    //provides implementation to the Api interface methods
    @Singleton
    @Provides
    fun providesApi(retrofit: Retrofit) : Api {
        return retrofit.create(Api::class.java)
    }
}
