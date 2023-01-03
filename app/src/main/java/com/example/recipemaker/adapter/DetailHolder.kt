package com.example.recipemaker.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemaker.databinding.ItemDetailBinding

import com.example.recipemaker.domain.model.Recipe

class DetailHolder (item: View) : RecyclerView.ViewHolder(item){

    private var binding = ItemDetailBinding.bind(item)


    fun init(recipe: String, onClickListener: (Int) -> Unit){
        binding.itemDetail.text = recipe


        itemView.setOnClickListener{
            onClickListener(adapterPosition)
        }
    }

}