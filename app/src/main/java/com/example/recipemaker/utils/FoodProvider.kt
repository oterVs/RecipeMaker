package com.example.recipemaker.utils

import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.domain.model.User

class FoodProvider {
    companion object {
        var food: List<Recipe> = emptyList()
        var foodMy: List<Recipe> = emptyList()
        var foodFav: List<Recipe> = emptyList()
        var itemSelected: Recipe = Recipe("uno")
        var userLogger: User = User()
        public const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123

    }
}