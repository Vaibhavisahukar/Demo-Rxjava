package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.ResultsItem
import com.example.myapplication.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val disposable = CompositeDisposable()

    val allUsers: LiveData<List<ResultsItem>> = userRepository.allUsers

    init {
        deleteUsers()
        userRepository.callUserApi()
    }

    fun getUser() {
        userRepository.callUserApi()
    }

    fun deleteUser(resultsItem: ResultsItem): Single<Int> {
        return userRepository.deleteUser(resultsItem).subscribeOn(Schedulers.io())
    }

    fun deleteUsers(): Completable {
        return userRepository.deleteAllUsers()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
