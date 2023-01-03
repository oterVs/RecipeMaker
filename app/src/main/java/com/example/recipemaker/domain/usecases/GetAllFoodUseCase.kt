package com.example.recipemaker.domain.usecases

import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.domain.repository.FoodRepository
import com.example.recipemaker.domain.repository.LoginRepository
import com.example.recipemaker.utils.DataState
import dagger.Binds
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllFoodUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {


    suspend operator fun invoke(): Flow<DataState<List<Recipe>>> =
        foodRepository.getAllFood()


}