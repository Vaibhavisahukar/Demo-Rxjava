package com.example.myapplication.retrofit

import com.example.myapplication.model.User
import io.reactivex.Single
import retrofit2.http.GET

interface Api {

    @GET("?results=20")
    fun getUserDetails(): Single<User>
}
