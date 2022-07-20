package com.example.myapplication.repository

import com.example.myapplication.model.User
import com.example.myapplication.retrofit.Api
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor(private val api: Api) {
    fun modelObservable(): Single<User> {
        return api.getUserDetails()
    }
}
