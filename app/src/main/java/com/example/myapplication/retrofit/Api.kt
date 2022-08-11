package com.example.myapplication.retrofit

import com.example.myapplication.model.User
import io.reactivex.Flowable
import retrofit2.http.GET

interface Api {

    @GET("?results=5")
    fun getUserDetails(): Flowable<User>
}
