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
    //lateinit var auth : FirebaseAuth

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
        /*
        navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.profileFragment-> {
                    openFragment(HomeFragment.newInstance("", ""))
                    println("1")
                }
                R.id.photoFragment-> {
                    println("2")
                }
                R.id.searchFragment -> {

                }
            }
            return@setOnItemSelectedListener true
        }*/
       // navController.navigate()
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        foodeModel.onCreate()


        profileViewModel.userExist(dataStore.getUserName())

        //profileViewModel.getFavoriteFood(FoodProvider.userLogger.favorites)

       // profileViewModel.getFavoriteMine(FoodProvider.userLogger.recipes)





        //foodeModel.getEasyFood()
/*
        binding.logInTest.setOnClickListener{
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googlesigninclient = GoogleSignIn.getClient(this,gso)
            googlesigninclient.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()

        }*/
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.profileFragment, R.id.photoFragment, R.id.searchFragment
            )
        )


        //setupActionBarWithNavController(navController, appBarConfiguration)
         navView.setupWithNavController(navController)

        //auth = FirebaseAuth.getInstance()
       // binding.userLogIn.text = intent.getStringExtra("email")
       // userViewModel.setUser(intent.getStringExtra("email") ?: "Default")
        /*binding.buttonLogOut.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
        }*/



    }

    private fun initObservers(){
        profileViewModel.userExistD.observe(this, Observer{
            when(it){
                is DataState.Success<User> -> {
                    // activity?.toast(it.data.email)
                    // activity?.toast(dataStore.getStoreIsLogIn().toString())

                    FoodProvider.userLogger = it.data
                    println(FoodProvider.userLogger.name)
                    println(FoodProvider.userLogger.favorites)
                    //binding.nameProfile.text = it.data.name
                    //Picasso.get().load(it.data.photoUrl).into(binding.profileimg)

                }
                else -> Unit
            }
        })


        profileViewModel.favoriteFood.observe(this, Observer {
            when(it){
                is DataState.Success<List<Recipe>> -> {
                    // activity?.toast(it.data.email)

                   // FoodProvider.foodFav = it.data.toMutableList()
                    println(FoodProvider.foodFav)
                    //hideProgressDialog()
                }
                is DataState.Error -> {
                    Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                    //hideProgressDialog()
                    //manageRegisterErrorMessages(it.exception)
                }
                is DataState.Loading ->{
                    //showProgressBar()
                }
                else -> {
                    Toast.makeText(this,"algo mas",Toast.LENGTH_SHORT).show()
                }
            }
        })

        profileViewModel.favoriteMine.observe(this, Observer {
            when(it){
                is DataState.Success<List<Recipe>> -> {
                    // activity?.toast(it.data.email)

                    FoodProvider.foodMy = it.data.toMutableList()
                    //println(FoodProvider.foodFav)
                    //activity?.toast(it.data.size.toString())

                    // hideProgressDialog()
                }
                is DataState.Error -> {
                    //hideProgressDialog()
                    //manageRegisterErrorMessages(it.exception)
                }
                is DataState.Loading ->{
                    //showProgressBar()
                }
                else -> { }
            }
        })
    }
}


