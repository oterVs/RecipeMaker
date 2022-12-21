package com.example.recipemaker.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemaker.databinding.ItemFoodBinding
import com.example.recipemaker.model.Recipe
import com.squareup.picasso.Picasso

class FoodHolder(inflate: View) : RecyclerView.ViewHolder(inflate) {

    private var binding = ItemFoodBinding.bind(inflate)

    fun init(food: Recipe){
        binding.titleFood.text = food.title
        Picasso.get().load(food.imageUrl).into(binding.imgItemFood)
    }
}