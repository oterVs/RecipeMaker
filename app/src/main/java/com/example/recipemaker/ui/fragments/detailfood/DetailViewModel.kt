package com.example.recipemaker.ui.fragments.detailfood

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipemaker.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(): ViewModel() {


    val _itemSelected : MutableLiveData<Recipe> = MutableLiveData()
    val itemSelected: LiveData<Recipe>
        get() = _itemSelected



    fun selectedItem(item: Recipe){
        _itemSelected.value = item
    }


}