package com.example.recipemaker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.recipemaker.adapter.DetailAdapter
import com.example.recipemaker.databinding.FragmentDetailFoodBinding
import com.example.recipemaker.databinding.FragmentProfileBinding
import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.ui.detail.DetailViewModel
import com.example.recipemaker.ui.login.LoginViewModel
import com.example.recipemaker.ui.login.SignUpViewModel
import com.example.recipemaker.ui.search.RecicleRecipeViewModel
import com.example.recipemaker.utils.FoodProvider
import com.example.recipemaker.utils.toast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFood.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DetailFood : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var binding : FragmentDetailFoodBinding
    private val detailFood : DetailViewModel by viewModels()
    private val signupView : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     /* setFragmentResultListener("requestKey") { key, bundle ->
          // We use a String here, but any type that can be put in a Bundle is supported
          val result = bundle.getString("bundleKey")
          // Do something with the result...
      }*/


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailFoodBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initRecipe()
    }

    private fun initListeners() {
      //  activity?.toast(modelRecipe._itemSelected.)
        //binding.titelDetailFood.text = modelRecipe.itemDataSelected?.title
        var like = false
        binding.backSearch.setOnClickListener{
            findNavController().navigate(R.id.searchFragment)
        }
        binding.addFavorite.setOnClickListener {

            like = likeAnimation(binding.addFavorite, R.raw.lotie1, like)
            if(!FoodProvider.userLogger.favorites.contains(FoodProvider.itemSelected.id)){
                FoodProvider.userLogger.favorites.add(FoodProvider.itemSelected.id)
                signupView.saveUser(FoodProvider.userLogger)
            }

            //loginView.saveUser()
        }
        detailFood.itemSelected.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity as LogIn, it.title, Toast.LENGTH_SHORT).show()
            println(it.title)
            //binding.titelDetailFood.text = it.title
        })

    }

    private fun likeAnimation(imageView: LottieAnimationView, animation: Int, like: Boolean): Boolean {
        if(!like){
            imageView.setAnimation(animation)
            imageView.playAnimation()
        } else {
            imageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
        return !like
    }


    private fun initRecipe(){
        val food = FoodProvider.itemSelected
        binding.titelDetailFood.text = food.id

        binding.rvSteps.adapter = DetailAdapter(food.steps.toMutableList()) {

        }

        Picasso.get().load(food.imageUrl).into(binding.ivDetailFood)
    }




}