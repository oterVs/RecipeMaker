package com.example.recipemaker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemaker.R

class DetailAdapter (private  val list : MutableList<String>, private val onClickLister: (Int) -> Unit) : RecyclerView.Adapter<DetailHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return DetailHolder(layoutInflater.inflate(R.layout.item_detail,parent,false))
    }

    override fun onBindViewHolder(holder: DetailHolder, position: Int) {
        val position = list[position]
        holder.init(position, onClickLister)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}