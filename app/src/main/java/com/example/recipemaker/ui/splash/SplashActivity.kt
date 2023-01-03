package com.example.recipemaker.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.recipemaker.LogIn
import com.example.recipemaker.MainActivity
import com.example.recipemaker.ui.login.LoginViewModel
import com.example.recipemaker.utils.Constants.SHARED_EMAIL
import com.example.recipemaker.utils.Constants.SHARED_PASSWORD
import com.example.recipemaker.utils.DataState
import com.example.recipemaker.utils.toast
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel : DataStoreViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //startActivity(Intent(this, MainActivity::class.java))
        //finish()
       // println(auth.currentUser?.uid ?: "No hay usuario")

        //initObservers()
        //viewModel.storeIsLogIn(false)
        println(viewModel.getStoreIsLogIn().toString())
        if (viewModel.getStoreIsLogIn()){
            startActivity(Intent(this, LogIn::class.java))
            finish()
        } else {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    /*
    private fun initObservers(){
        viewModel.loginState.observe(this, Observer { dataState ->
            when(dataState){
                is DataState.Success<Boolean> -> {
                    viewModel.getUserData()
                }
                is DataState.Error -> {
                    toast("La contraseña guardada ya no es válida")
                }

                else -> Unit
            }
        })

        viewModel.userDataState.observe(this, Observer { dataState ->
            when(dataState){
                is DataState.Success<Boolean> -> {

                    startActivity(Intent(this, LogIn::class.java))
                    finish()
                }
                is DataState.Error -> {
                    toast("La contraseña guardada ya no es válida")
                }

                else -> Unit
            }
        })


    }

    private fun isUserSaved(): Boolean{
        return getSavedEmail()?.isNotEmpty() == true && getSavedPassword()?.isNotEmpty() == true
    }

    private fun getSavedEmail() = sharedPreferences.getString(SHARED_EMAIL, "")
    private fun getSavedPassword() = sharedPreferences.getString(SHARED_PASSWORD, "")
*/
}