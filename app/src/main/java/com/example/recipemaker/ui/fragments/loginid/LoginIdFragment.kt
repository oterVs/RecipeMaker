package com.example.recipemaker.ui.fragments.loginid

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.recipemaker.R
import com.example.recipemaker.databinding.FragmentLoginIdBinding
import com.example.recipemaker.domain.model.User
import com.example.recipemaker.domain.model.UserApiItem
import com.example.recipemaker.domain.model.UserResponse
import com.example.recipemaker.ui.activities.LogIn
import com.example.recipemaker.ui.fragments.login.LoginViewModel
import com.example.recipemaker.ui.fragments.profile.ProfileViewModel
import com.example.recipemaker.ui.fragments.session.DataStoreViewModel
import com.example.recipemaker.utils.*
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginIdFragment : Fragment() {

    private lateinit var binding : FragmentLoginIdBinding

    private var usuariosApi : MutableList<UserApiItem> = mutableListOf()

    private val viewModel : LoginViewModel by viewModels()
    private val dataStore : DataStoreViewModel by viewModels()
    private val profileViewModel : ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginIdBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUsers()
        initListeners()
        initObservers()
    }

    private fun initObservers(){
        viewModel.usersapi.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<UserResponse> -> {
                    usuariosApi = dataState.data
                }
                else -> Unit
            }
        })
        viewModel.loginState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<Boolean> -> {
                    viewModel.getUserData()
                }
                is DataState.Error -> {
                    hideProgressDialog()
                    manageLoginErrorMessages(dataState.exception)
                }
                is DataState.Loading ->{
                    showProgressBar()
                }
                else -> Unit
            }
        })

        viewModel.userDataState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<Boolean> -> {

                    hideProgressDialog()
                    manageUserLogin()
                    startActivity(Intent(requireContext(), LogIn::class.java))
                    activity?.finish()
                }
                is DataState.Error -> {
                    hideProgressDialog()
                    manageLoginErrorMessages(dataState.exception)
                }
                is DataState.Loading ->{
                    showProgressBar()
                }
                else -> Unit
            }
        })

        profileViewModel.userExistD.observe(viewLifecycleOwner, Observer{
            when(it){
                is DataState.Success<User> -> {
                    // activity?.toast(it.data.email)

                    FoodProvider.userLogger = it.data


                }
                else -> Unit
            }
        })
    }

    private fun initListeners(){
        binding.changeLog.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(isChecked){
                findNavController().navigate(R.id.fragmentLogIn)
            }
        }
        binding.buttonLogin.setOnClickListener{
            loginUser()
        }
    }

    private fun loginUser() {
        if(userDataOk()){
           // showProgressBar()

            var id = binding.etUser.text.toString().trim()
            var email = ""
            var password = ""

            var isactive = false


            for(user in usuariosApi){
                println("User: ${user.id} Status:${user.status}")
                if(user.id.toString() == id && user.status == "active"){
                    email = user.email
                    password = "1234567"
                    isactive = true
                    dataStore.storeEmail(user.email)
                    break;
                }
            }

            if(isactive){
                viewModel.login(email, password)
                //activity?.toast("Se inicia sesión")
            } else {
                activity?.toast("Usuario inactivo")
            }


        }
    }

    private fun manageUserLogin() {

       // dataStore.storeEmail(binding.etUser.text.toString())
        dataStore.storeIsLogIn(true)
        //profileViewModel.userExist(dataStore.getUserName())
    }


    private fun showProgressBar() {
        binding.buttonLogin.text = ""
        binding.buttonLogin.isEnabled = false
        binding.pbSignIn.visibility = View.VISIBLE
    }
    private fun manageLoginErrorMessages(exception: Exception) {
        when(exception.message){
            Constants.USER_NOT_EXISTS -> { activity?.snackBar(getString(R.string.login__error_user_no_registered),binding.buttonLogin) }
            Constants.WRONG_PASSWORD -> { activity?.snackBar(getString(R.string.login__error_wrong_password),binding.buttonLogin) }
            else -> { activity?.snackBar(getString(R.string.login__error_unknown_error),binding.buttonLogin) }
        }
    }
    private fun hideProgressDialog() {
        binding.pbSignIn.visibility = View.GONE
        binding.buttonLogin.text = getString(R.string.login__login_button)
        binding.buttonLogin.isEnabled = true
    }



    private fun userDataOk(): Boolean {
        return when {
            requireActivity().isInputEmpty(binding.etUser, true) -> {
                activity?.snackBar("Rellene los datos",binding.etUser)
                false
            }
            else -> {true}
        }
    }


}