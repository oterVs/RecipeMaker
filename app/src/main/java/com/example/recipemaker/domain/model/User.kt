package com.example.recipemaker.domain.model

import com.example.recipemaker.utils.Constants.INFO_NOT_SET

data class User(
    val email: String = INFO_NOT_SET,
    val id: String = INFO_NOT_SET,
    val photoUrl: String = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.flaticon.es%2Ficono-gratis%2Fcocinero_3461980&psig=AOvVaw35kWY_pVblrL-Brm7F4CIi&ust=1673903139664000&source=images&cd=vfe&ved=0CBAQjRxqFwoTCOCe36G9yvwCFQAAAAAdAAAAABAE",
    val name: String = "Chefsito",
    val favorites: MutableList<String> = mutableListOf<String>(),
    val recipes: MutableList<String> = mutableListOf<String>()
){

}