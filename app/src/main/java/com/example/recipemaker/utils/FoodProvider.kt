package com.example.recipemaker.utils

import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.domain.model.User

class FoodProvider {
    companion object {

        //todas las recetas bdd
        var food: List<Recipe> = emptyList()

        //item seleccionado en los RV de recetas
        var itemSelected: Recipe = Recipe("uno")

        //usuario logeado
        var userLogger: User = User()

        //permisos usuario
        public const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123

    }
}