package com.example.recipemaker.ui.search

import android.speech.RecognizerIntent
import androidx.lifecycle.*
import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.domain.usecases.GetAllFoodUseCase
import com.example.recipemaker.domain.usecases.GetEasyFoodUseCase
import com.example.recipemaker.utils.DataState
import com.example.recipemaker.utils.FoodProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecicleRecipeViewModel @Inject constructor(
    val getAllFoodUseCase: GetAllFoodUseCase

) : ViewModel() {


    val getEasyFoodUseCase = GetEasyFoodUseCase()




    val _itemSelected = MutableLiveData<Recipe>()
    val quantity: LiveData<Recipe>
        get() = _itemSelected


    var itemDataSelected: Recipe? = null


    val _allFood = MutableLiveData< DataState<List<Recipe>>>()
    val allFood : LiveData<DataState<List<Recipe>>>
        get() = _allFood


    val _easyFood = MutableLiveData<List<Recipe>>()
    val easyFood : LiveData<List<Recipe>>
        get() = _easyFood



    fun onCreate () {
        viewModelScope.launch {
            getAllFoodUseCase()
                .onEach() {
                    _allFood.value = it

                }.launchIn(viewModelScope)
        }
    }

    fun getEasyFood(){
        _easyFood.value = getEasyFoodUseCase()
    }

    fun setItemSelection(item: Recipe) {
        //_itemSelected.value = item
        itemDataSelected = item
    }
}