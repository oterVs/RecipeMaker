package com.example.recipemaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recipemaker.databinding.ActivityLogInBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.AppBarConfiguration
import com.example.recipemaker.ui.login.LoginViewModel
import com.example.recipemaker.ui.search.RecicleRecipeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogIn : AppCompatActivity() {

    lateinit var binding : ActivityLogInBinding
    //lateinit var auth : FirebaseAuth
    private val foodeModel : RecicleRecipeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

       val navController = findNavController(R.id.nav_host_fragment_activity_main)

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
}


