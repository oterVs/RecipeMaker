package com.example.recipemaker.ui.rview.holder

import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemaker.R
import com.example.recipemaker.databinding.ItemFoodBinding
import com.example.recipemaker.domain.model.Recipe
import com.squareup.picasso.Picasso

class FoodHolder(inflate: View, ) : RecyclerView.ViewHolder(inflate) {

    private var binding = ItemFoodBinding.bind(inflate)

    fun init(food: Recipe, onClickListener:(Recipe) -> Unit, widthcard: Int, heightcar: Int){
        binding.titleFood.text = food.title

        Picasso.get().load(food.imageUrl).into(binding.imgItemFood)

        itemView.setOnClickListener{
            onClickListener(food)
        }

        binding.card.startAnimation(AnimationUtils.loadAnimation(itemView.context, R.anim.anim_one))

        val layoutParams = binding.card.layoutParams
        val layoutParamsi = binding.imgItemFood.layoutParams

        layoutParams.width = widthcard
        layoutParams.height = heightcar
        layoutParamsi.height = heightcar - (heightcar * 0.20).toInt()

        binding.card.layoutParams = layoutParams


    }


}