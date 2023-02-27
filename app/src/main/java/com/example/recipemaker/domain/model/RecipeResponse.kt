package com.example.recipemaker.domain.model

import java.util.UUID


data class Recipe(

    val title: String = "",
    val duration: String = "",
    val id: String = UUID.randomUUID().toString(),
    val imageUrl: String = "https://i0.wp.com/images-prod.healthline.com/hlcmsresource/images/AN_images/healthy-eating-ingredients-1296x728-header.jpg?w=1155&h=1528",
    val ingredients: List<String> = emptyList(),
    val steps: List<String> = emptyList()


){

}