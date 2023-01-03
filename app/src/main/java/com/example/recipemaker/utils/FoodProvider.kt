package com.example.recipemaker.utils

import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.domain.model.User

class FoodProvider {
    companion object {
        var food: List<Recipe> = emptyList()
        var itemSelected: Recipe = Recipe("uno")
        var userLogger: User = User()
    }
}