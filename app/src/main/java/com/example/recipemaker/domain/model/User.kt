package com.example.recipemaker.domain.model

import com.example.recipemaker.utils.Constants.INFO_NOT_SET

data class User(
    val email: String = INFO_NOT_SET,
    val id: String = INFO_NOT_SET,
    val photoUrl: String = "https://img.freepik.com/vector-premium/icono-chef-negro_602006-3234.jpg",
    val name: String = "Chefsito",
    val favorites: MutableList<String> = mutableListOf<String>(),
    val recipes: MutableList<String> = mutableListOf<String>()
){

}