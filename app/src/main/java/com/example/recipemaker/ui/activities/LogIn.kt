package com.example.recipemaker.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recipemaker.databinding.ActivityLogInBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.AppBarConfiguration
import com.example.recipemaker.R
import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.domain.model.User
import com.example.recipemaker.ui.fragments.profile.ProfileViewModel
import com.example.recipemaker.ui.fragments.search.RecicleRecipeViewModel
import com.example.recipemaker.ui.fragments.session.DataStoreViewModel
import com.example.recipemaker.utils.DataState
import com.example.recipemaker.utils.FoodProvider
import com.example.recipemaker.utils.toast
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogIn : AppCompatActivity() {

    lateinit var binding : ActivityLogInBinding


    private val foodeModel : RecicleRecipeViewModel by viewModels()
    private val dataStore : DataStoreViewModel by viewModels()
    private val profileViewModel : ProfileViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        initObservers()



        // se recuperan las recetas
        foodeModel.onCreate()

        //se recuperan los datos del usuario
        profileViewModel.userExist(dataStore.getUserName())


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.profileFragment, R.id.photoFragment, R.id.searchFragment
            )
        )

        //init navegacion
         navView.setupWithNavController(navController)



    }

    private fun initObservers(){

        profileViewModel.userExistD.observe(this, Observer{
            when(it){
                is DataState.Success<User> -> {
                    FoodProvider.userLogger = it.data

                }
                else -> Unit
            }
        })


    }
}


