package com.example.recipemaker.ui.search

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipemaker.LogIn
import com.example.recipemaker.R
import com.example.recipemaker.adapter.FoodAdapter
import com.example.recipemaker.databinding.FragmentSearchBinding
import com.example.recipemaker.model.Recipe
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private lateinit var firestore: FirebaseFirestore
    private val listFood : MutableList<Recipe> = mutableListOf(Recipe("uno"));
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        setup()
        return binding.root
    }

    private fun setup(){
        firestore = FirebaseFirestore.getInstance()
        var query: Query = firestore.collection("foodList")
        firestore.collection("foodList")
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {

                   //
                   // Log.d(TAG, "${document.id} => ${document.data}")
                    var recipe1 = document.toObject(Recipe::class.java)
                    println(recipe1.title)
                    listFood.add(document.toObject(Recipe::class.java))
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        //println(listFood.size)
        binding.rvFood.adapter = FoodAdapter(listFood)
      //  binding.rvFood.layoutManager = LinearLayoutManager(activity as LogIn)
/*
        binding.nextImg.setOnClickListener{
            binding.imgCarrusel.setImageResource(R.drawable.google)
        }
        binding.previosImag.setOnClickListener{
            binding.imgCarrusel.setImageResource(R.drawable.facebook)
        }
        */

    }
}