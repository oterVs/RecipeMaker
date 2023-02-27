package com.example.recipemaker.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView
import com.example.recipemaker.R
import com.example.recipemaker.databinding.ActivitySplashBinding
import com.example.recipemaker.domain.model.User
import com.example.recipemaker.ui.fragments.profile.ProfileViewModel

import com.example.recipemaker.ui.fragments.session.DataStoreViewModel
import com.example.recipemaker.utils.DataState
import com.example.recipemaker.utils.FoodProvider

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {


    lateinit var binding: ActivitySplashBinding
    private val viewModel : DataStoreViewModel by viewModels()
    private val profileViewModel : ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var like = true

        //se inicia la animación
        binding.lotiesplash.playAnimation()

        observers()

        //deley del splash
        Handler().postDelayed( {


            //si ya se logeo vamos a su perfil caso contrario al login
            if (viewModel.getStoreIsLogIn()){
               // profileViewModel.userExist(viewModel.getUserName())
                startActivity(Intent(this@SplashActivity, LogIn::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
            finish()
        }, 4000)





    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        // menu.add("Save");
        return true
    }

    private fun observers(){
        profileViewModel.userExistD.observe(this, Observer{
            when(it){
                is DataState.Success<User> -> {

                    FoodProvider.userLogger = it.data

                }
                else -> Unit
            }
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

}