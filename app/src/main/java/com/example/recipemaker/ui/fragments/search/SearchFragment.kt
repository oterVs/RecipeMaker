package com.example.recipemaker.ui.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemaker.R
import com.example.recipemaker.ui.rview.adapter.FoodAdapter
import com.example.recipemaker.databinding.FragmentSearchBinding
import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.ui.fragments.detailfood.DetailViewModel
import com.example.recipemaker.utils.FoodProvider
import com.example.recipemaker.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private var list : MutableList<Recipe> = mutableListOf()
    private lateinit var searchView: SearchView
    private lateinit var adapter: FoodAdapter
    private val modelRecipe : RecicleRecipeViewModel by viewModels()
    private val detailFood : DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchView = binding.searchView
        list = FoodProvider.food.toMutableList()
        adapter = FoodAdapter(list,525, 400){ recipe ->
            onItemSelected(recipe)
        }
        binding.rvFood.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        //modelRecipe.getEasyFood()

        initObserver()

        //println(listFood.size)
        /*binding.rvFood.adapter = FoodAdapter(listFood){
            onItemSelected(it)
        }*/
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
    fun filterList(text: String?){
        if(text != null){
            val filteredList = ArrayList<Recipe>()
            for(i in list){
                if(i.title.toLowerCase(Locale.ROOT).contains(text)){
                    filteredList.add(i)
                }
            }
            if(filteredList.isEmpty()){
                activity?.toast("No se encontraron coincidencias")
            } else{
                adapter.setData(filteredList)
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun initObserver(){
        /*modelRecipe.allFood.observe(viewLifecycleOwner, Observer {
            when(it){
                is DataState.Success<List<Recipe>> -> {
                    binding.rvFood.adapter = FoodAdapter(it.data){ recipe ->
                        onItemSelected(recipe)
                    }
                }
                else -> Unit
            }
        })*/

        binding.swipeRv.setOnRefreshListener {
            //listCountries.clear()
            //loadCountries()
            binding.swipeRv.isRefreshing = false
        }

        binding.rvFood.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(-1)) {
                    activity?.toast("halo")
                }
            }
        })

        modelRecipe.easyFood.observe(viewLifecycleOwner, Observer {
            list = it.toMutableList()
            binding.rvFood.adapter = FoodAdapter(list,1625,400){ recipe ->
                onItemSelected(recipe)
            }
        })

        modelRecipe.quantity.observe(viewLifecycleOwner, Observer {
            //Toast.makeText(activity as LogIn, it.title, Toast.LENGTH_SHORT).show()
            println(it.title)
            //binding.titelDetailFood.text = it.title
        })
    }


    private fun setup(){


    }



    fun onItemSelected(food: Recipe){
        detailFood.selectedItem(food)
        FoodProvider.itemSelected = food
        val result = "result"
        // Use the Kotlin extension in the fragment-ktx artifact
        //setFragmentResult("requestKey", bundleOf("bundleKey" to food.title))
       findNavController().navigate(R.id.detailFood)
    }

}

interface ClickListener {
    fun itemSelect(data: Recipe)
}