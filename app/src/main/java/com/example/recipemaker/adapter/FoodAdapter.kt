package com.example.recipemaker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemaker.R
import com.example.recipemaker.model.Recipe

class FoodAdapter(val lista: List<Recipe>) : RecyclerView.Adapter<FoodHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FoodHolder(layoutInflater.inflate(R.layout.item_food,parent,false))
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        var item = lista[position]
        holder.init(item)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}