package com.example.recipemaker.domain.repository
import android.provider.ContactsContract.Data
import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.utils.DataState
import kotlinx.coroutines.flow.Flow



interface FoodRepository {
    suspend fun getAllFood(): Flow<DataState<List<Recipe>>>
    suspend fun getFavoriteFood(lista: List<String>): Flow<DataState<List<Recipe>>>
    suspend fun saveFood(food: Recipe): Flow<DataState<Boolean>>

}