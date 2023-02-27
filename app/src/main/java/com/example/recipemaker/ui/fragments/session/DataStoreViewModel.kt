package com.example.recipemaker.ui.fragments.session

import androidx.lifecycle.ViewModel
import com.example.recipemaker.domain.interfaces.DataStoreRepository
import com.example.recipemaker.utils.Constants.IS_SIGN_IN

import com.example.recipemaker.utils.Constants.USER_EMAIL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val dataStore: DataStoreRepository
) : ViewModel() {


    fun storeEmail(value: String) = runBlocking {
        dataStore.putString(USER_EMAIL, value)
    }

    fun getUserName(): String = runBlocking{
        dataStore.getString(USER_EMAIL)!!
    }

    fun storeIsLogIn(value: Boolean) = runBlocking{
        dataStore.putBoolean(IS_SIGN_IN, value)
    }

    fun getStoreIsLogIn(): Boolean = runBlocking {
        dataStore.getBoolean(IS_SIGN_IN) ?: false
    }
}