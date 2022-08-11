package com.example.myapplication.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.database.DataDao
import com.example.myapplication.database.UserDatabase
import com.example.myapplication.model.ResultsItem
import com.example.myapplication.model.User
import com.example.myapplication.retrofit.Api
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: Api,
    private val dataDao: DataDao,
    private val userDatabase: UserDatabase
) {

    private var _isInProgress = MutableLiveData<Boolean>()
    private var _isError = MutableLiveData<Boolean>()
    val allUsers: LiveData<List<ResultsItem>> = dataDao.getData()

    fun callUserApi(): Disposable {
        return api.getUserDetails()
            .subscribeOn(Schedulers.io())
            .subscribeWith(subscribeToDatabase())
    }

    private fun subscribeToDatabase(): DisposableSubscriber<User> {
        return object : DisposableSubscriber<User>() {

            override fun onNext(user: User?) {
                if (user != null) {
                    val userList = user.results?.toList()
                    userDatabase.apply {
                        dataDao.insertData(userList)
                    }
                }
            }

            override fun onError(t: Throwable?) {
                _isInProgress.postValue(true)
                Log.e("insertData()", "error: ${t?.message}")
                _isError.postValue(true)
                _isInProgress.postValue(false)
            }

            override fun onComplete() {
                Log.v("insertData()", "insert success")
            }
        }
    }

    fun deleteUser(resultsItem: ResultsItem): Single<Int> {
        return Single.fromCallable { dataDao.delete(resultsItem) }
    }

    fun deleteAllUsers(): Completable {
        return Completable.fromAction { dataDao.deleteUsers() }.subscribeOn(Schedulers.io())

    }
}
