package com.example.recipemaker.domain.usecases

import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.utils.FoodProvider

class GetEasyFoodUseCase {
    operator fun invoke(): List<Recipe> {
        return FoodProvider.food.subList(0, 10)
    }
}