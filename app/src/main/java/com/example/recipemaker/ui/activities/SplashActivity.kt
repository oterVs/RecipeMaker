package com.example.recipemaker.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.recipemaker.R
import com.example.recipemaker.databinding.ActivitySplashBinding
import com.example.recipemaker.ui.fragments.session.DataStoreViewModel

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {


    lateinit var binding: ActivitySplashBinding
    private val viewModel : DataStoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var like = true
       // like = likeAnimation(binding.lotiesplash, R.raw.food, like)

        binding.lotiesplash.playAnimation()
        //startActivity(Intent(this, MainActivity::class.java))
        //finish()
        // println(auth.currentUser?.uid ?: "No hay usuario")
        //initObservers()
        //viewModel.storeIsLogIn(false)

        Handler().postDelayed( { //Aqui colocas la transición a otro activity
            println(viewModel.getStoreIsLogIn().toString())
            if (viewModel.getStoreIsLogIn()){
                startActivity(Intent(this@SplashActivity, LogIn::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
            finish()
        }, 4000)





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