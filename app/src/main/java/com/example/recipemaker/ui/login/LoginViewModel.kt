package com.example.recipemaker.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val userModel = MutableLiveData<String>()
    val quantity: LiveData<String> = userModel


    fun setUser(user: String){
        println(user)
        userModel.postValue(user)
    }

}