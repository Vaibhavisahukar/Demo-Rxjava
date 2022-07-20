package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.User
import com.example.myapplication.repository.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    private val disposable = CompositeDisposable()
    private val modelMutableLiveData: MutableLiveData<User> = MutableLiveData<User>()
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun getModelMutableLiveData(): MutableLiveData<User> {
        loadData()
        return modelMutableLiveData
    }

    private fun loadData() {
        isLoading.value = true
        disposable.add(userRepository.modelObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<User>() {
                override fun onSuccess(userModel: User) {
                    isLoading.value = false
                    modelMutableLiveData.setValue(userModel)
                }
                override fun onError(e: Throwable) {
                    error.value = e.message
                    isLoading.value = false
                }
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
