package com.example.recipemaker.ui.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.domain.model.User
import com.example.recipemaker.domain.interfaces.FoodRepository
import com.example.recipemaker.domain.interfaces.LoginRepository
import com.example.recipemaker.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _userExist : MutableLiveData<DataState<User>> = MutableLiveData()
    val userExistD : LiveData<DataState<User>>
        get() = _userExist

    private val _favoriteFood: MutableLiveData<DataState<List<Recipe>>> = MutableLiveData()
    val favoriteFood: LiveData<DataState<List<Recipe>>>
        get() = _favoriteFood

    private val _favoriteMine: MutableLiveData<DataState<List<Recipe>>> = MutableLiveData()
    val favoriteMine: LiveData<DataState<List<Recipe>>>
        get() = _favoriteMine


    fun userExist(email: String){
        viewModelScope.launch {
            loginRepository.getUser(email).onEach {
                _userExist.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun getFavoriteFood(list: List<String>){
        viewModelScope.launch{
            foodRepository.getFavoriteFood(list).onEach{
                _favoriteFood.value = it

            }.launchIn(viewModelScope)
        }
    }

    fun getFavoriteMine(list: List<String>){
        viewModelScope.launch{
            foodRepository.getFavoriteFood(list).onEach{
                _favoriteMine.value = it
            }.launchIn(viewModelScope)
        }
    }

}