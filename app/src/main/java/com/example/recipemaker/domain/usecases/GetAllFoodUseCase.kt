package com.example.recipemaker.domain.usecases

import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.domain.interfaces.FoodRepository
import com.example.recipemaker.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFoodUseCase @Inject constructor(
    private val foodRepository: FoodRepository
) {


    suspend operator fun invoke(): Flow<DataState<List<Recipe>>> =
        foodRepository.getAllFood()


}