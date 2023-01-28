package com.example.recipemaker.ui.fragments.login

import android.database.DataSetObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.example.recipemaker.domain.model.User
import com.example.recipemaker.domain.model.UserResponse
import com.example.recipemaker.domain.repository.LoginRepository
import com.example.recipemaker.domain.repository.UserApiRepository
import com.example.recipemaker.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userApiRepository: UserApiRepository
): ViewModel() {

    private val _loginState :MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val loginState : LiveData<DataState<Boolean>>
        get() = _loginState

    private val _userDataState :MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val userDataState : LiveData<DataState<Boolean>>
        get() = _userDataState

    private val _logOutState :MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val logOutState : LiveData<DataState<Boolean>>
        get() = _logOutState


    private val _usersapi : MutableLiveData<DataState<UserResponse>> = MutableLiveData()
    val usersapi : LiveData<DataState<UserResponse>>
        get() = _usersapi


    fun getUsers(){
        viewModelScope.launch {
            userApiRepository.getUsers().onEach { dataState ->
                _usersapi.value = dataState
            }.launchIn(viewModelScope)
        }
    }


    fun login(email: String, password: String){
        viewModelScope.launch {
            loginRepository.login(email,password).onEach { dataState ->
                _loginState.value = dataState
            }.launchIn(viewModelScope)
        }
    }

    fun getUserData(){
        viewModelScope.launch {
            loginRepository.getUserData().onEach { dataState ->
                _userDataState.value = dataState
            }.launchIn(viewModelScope)
        }
    }
    fun logOut(){
        viewModelScope.launch {
            loginRepository.logOut().onEach { dataState ->
                _logOutState.value = dataState
            }.launchIn(viewModelScope)
        }
    }


}