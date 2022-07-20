package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.UserRepository

class ViewModelFactory @Inject constructor(private val userRepository: UserRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(userRepository) as T
    }
}
