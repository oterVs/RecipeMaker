package com.example.recipemaker.ui.rview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemaker.R
import com.example.recipemaker.ui.rview.holder.FoodHolder
import com.example.recipemaker.domain.model.Recipe

class FoodAdapter(private var lista: List<Recipe>, private var widthcard: Int, private val onClickListener:(Recipe) -> Unit) : RecyclerView.Adapter<FoodHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return FoodHolder(layoutInflater.inflate(R.layout.item_food,parent,false))
    }

    override fun onBindViewHolder(holder: FoodHolder, position: Int) {
        var item = lista[position]
        holder.init(item, onClickListener, widthcard)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    fun setData(newData: List<Recipe>) {
        lista = newData
    }
}