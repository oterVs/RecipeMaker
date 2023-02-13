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
       // like = likeAnimation(binding.lotiesplash, R.raw.food, like)

        binding.lotiesplash.playAnimation()
        observers()



        //startActivity(Intent(this, MainActivity::class.java))
        //finish()
        // println(auth.currentUser?.uid ?: "No hay usuario")
        //initObservers()
        //viewModel.storeIsLogIn(false)

        Handler().postDelayed( { //Aqui colocas la transición a otro activity
            println(viewModel.getStoreIsLogIn().toString())
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
                    // activity?.toast(it.data.email)
                    // activity?.toast(dataStore.getStoreIsLogIn().toString())

                    FoodProvider.userLogger = it.data
                    // println(FoodProvider.userLogger.name)
                    // println(FoodProvider.userLogger.favorites)
                    //binding.nameProfile.text = it.data.name
                    //Picasso.get().load(it.data.photoUrl).into(binding.profileimg)

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