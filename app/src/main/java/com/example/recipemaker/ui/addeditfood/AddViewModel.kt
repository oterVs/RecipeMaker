package com.example.recipemaker.ui.addeditfood

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.domain.model.User
import com.example.recipemaker.domain.repository.FoodRepository
import com.example.recipemaker.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _saveFoodState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val saveFoodState : LiveData<DataState<Boolean>>
        get() = _saveFoodState


    fun saveRecipe(food: Recipe){
        viewModelScope.launch {
            foodRepository.saveFood(food)
                .onEach { dataState ->
                    _saveFoodState.value = dataState
                }.launchIn(viewModelScope
                )
        }
    }

}