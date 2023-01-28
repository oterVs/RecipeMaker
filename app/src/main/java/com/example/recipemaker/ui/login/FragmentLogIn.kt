package com.example.recipemaker.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.recipemaker.LogIn
import com.example.recipemaker.MainActivity
import com.example.recipemaker.R
import com.example.recipemaker.databinding.FragmentLogInBinding
import com.example.recipemaker.domain.model.User
import com.example.recipemaker.domain.model.UserApiItem
import com.example.recipemaker.domain.model.UserResponse
import com.example.recipemaker.ui.splash.DataStoreViewModel
import com.example.recipemaker.utils.Constants.USER_NOT_EXISTS
import com.example.recipemaker.utils.Constants.WRONG_PASSWORD
import com.example.recipemaker.utils.DataState
import com.example.recipemaker.utils.isInputEmpty
import com.example.recipemaker.utils.snackBar
import com.example.recipemaker.utils.toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentLogIn : Fragment() {

    private lateinit var binding : FragmentLogInBinding
    private lateinit var firebaseAut : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient


    private var usuariosApi : MutableList<UserApiItem> = mutableListOf()

    private val viewModel : LoginViewModel by viewModels()
    private val viewModelS : SignUpViewModel by viewModels()
    private val dataStore : DataStoreViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLogInBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUsers()
        initObservers()
        initListeners()
        setup()

    }

    private fun setup(){
        firebaseAut = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity as MainActivity, gso)
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

    }

    private fun initListeners(){
        binding.buttonLogin.setOnClickListener{
            loginUser()
        }
        binding.registered.setOnClickListener {
            findNavController().navigate(R.id.registeredFragment)
        }
        binding.logInGoogle.setOnClickListener {
            signInGoogle()
        }
    }
    private fun manageUserLogin() {

        dataStore.storeEmail(binding.etUser.text.toString())
        dataStore.storeIsLogIn(true)
    }



    private fun loginUser(){
        if (isUserDataOk()){
            showProgressBar()

            val email = binding.etUser.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            var isactive = false

            for(user in usuariosApi){
                if(user.email == email && user.status == "inactive"){
                    isactive = true
                    break;
                }
            }

            if(!isactive){
                viewModel.login(email, password)
            } else {
                activity?.toast("Usuario inactivo")
            }

           // viewModel.login(email, password)
        }
    }

    private fun isUserDataOk(): Boolean{
        return when{
            requireActivity().isInputEmpty(binding.etUser, true) -> {
               // activity?.toast(getString(R.string.login__error_enter_email))
                activity?.snackBar(getString(R.string.login__error_enter_email),binding.buttonLogin)
                false
            }
            requireActivity().isInputEmpty(binding.etPassword, true) -> {
                activity?.snackBar(getString(R.string.login__error_enter_password),binding.buttonLogin)
                false
            }
            else ->{
                true // El usuario mete todos los datos
            }
        }
    }

    private fun manageLoginErrorMessages(exception: Exception) {
        when(exception.message){
            USER_NOT_EXISTS -> { activity?.snackBar(getString(R.string.login__error_user_no_registered),binding.buttonLogin) }
            WRONG_PASSWORD -> { activity?.snackBar(getString(R.string.login__error_wrong_password),binding.buttonLogin) }
            else -> { activity?.snackBar(getString(R.string.login__error_unknown_error),binding.buttonLogin) }
        }
    }

    private fun hideProgressDialog() {
        binding.pbSignIn.visibility = View.GONE
        binding.buttonLogin.text = getString(R.string.login__login_button)
        binding.buttonLogin.isEnabled = true
    }

    private fun showProgressBar() {
        binding.buttonLogin.text = ""
        binding.buttonLogin.isEnabled = false
        binding.pbSignIn.visibility = View.VISIBLE
    }


    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)

    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            resul ->
        if (resul.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(resul.data)
            handleResults(task)
        }
    }
    private fun handleResults(task: Task<GoogleSignInAccount>) {
        if(task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        }else {
            Toast.makeText(activity as MainActivity, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAut.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                //val intent : Intent = Intent(activity as MainActivity, LogIn::class.java)
                //intent.putExtra("email", account.email)
                //intent.putExtra("name", account.displayName)
                //userViewModel.setUser(account.email ?: "default")

                //viewModel.userExist(account.email ?: "no")
                viewModelS.saveUser(User(email = account.email ?: "Sin correo",id = account.email ?: "Sin id", photoUrl = account.photoUrl.toString() , name = account.familyName ?: "Chefsito"))
                dataStore.storeEmail(account.email ?: "Sin correo")
                dataStore.storeIsLogIn(true)
                startActivity(Intent(requireContext(), LogIn::class.java))
                activity?.finish()

            }else {
                Toast.makeText(activity as MainActivity, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }


}