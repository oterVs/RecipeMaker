package com.example.recipemaker.model

data class RecipeResponse (val lista: List<Recipe>) {
}


data class Recipe(

    val title: String = "",
    val duration: String = "",
    val imageUrl: String = "https://i0.wp.com/images-prod.healthline.com/hlcmsresource/images/AN_images/healthy-eating-ingredients-1296x728-header.jpg?w=1155&h=1528",
    val ingredientes: List<String> = emptyList(),
    val steps: List<String> = emptyList()

){

}