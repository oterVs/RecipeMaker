package com.example.recipemaker.ui.fragments.signup

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.recipemaker.ui.activities.LogIn
import com.example.recipemaker.ui.activities.MainActivity
import com.example.recipemaker.R
import com.example.recipemaker.databinding.FragmentRegisteredBinding


import com.example.recipemaker.domain.model.User
import com.example.recipemaker.utils.Constants.EMAIL_ALREADY_EXISTS
import com.example.recipemaker.utils.DataState
import com.example.recipemaker.utils.isInputEmpty
import com.example.recipemaker.utils.snackBar
import com.example.recipemaker.utils.toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisteredFragment : Fragment() {


    private lateinit var binding : FragmentRegisteredBinding
    private lateinit var firebaseAut : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private val viewModel : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisteredBinding.inflate(layoutInflater);
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
        setup()

    }

    private fun initObservers(){
        viewModel.signUpState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<User> -> {
                    viewModel.saveUser(user = dataState.data)
                }
                is DataState.Error -> {
                    hideProgressDialog()
                    manageRegisterErrorMessages(dataState.exception)
                }
                is DataState.Loading ->{
                    showProgressBar()
                }
                else -> Unit
            }
        })

        viewModel.saveUserState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<Boolean> -> {
                    activity?.toast(getString(R.string.signup__signup_successfully))
                    activity?.onBackPressed()
                }
                is DataState.Error -> {
                    hideProgressDialog()
                    manageRegisterErrorMessages(dataState.exception)
                }
                is DataState.Loading ->{
                    showProgressBar()
                }
                else -> { }
            }
        })
    }

    private fun initListeners(){
        binding.RegisteredEmail.setOnClickListener {
            if (isUserDataOk()){
                viewModel.signUp(createUser(), binding.repPassword1.text.toString())
            }
        }


        binding.backLogin.setOnClickListener{
            activity?.onBackPressed()
        }


        binding.registeredGoogle.setOnClickListener {
            signInGoogle()
        }
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
                viewModel.saveUser(User(email = account.email ?: "Sin correo",id = account.email ?: "Sin id"))
                startActivity(Intent(requireContext(), LogIn::class.java))
                activity?.finish()

            }else {
                Toast.makeText(activity as MainActivity, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setup(){

        firebaseAut = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity as MainActivity, gso)

    }

    private fun createUser(): User {

        val email = binding.retUser.text.toString()
        return User(
            email = email
        )
    }

    private fun isUserDataOk() : Boolean{
        return when {

            requireActivity().isInputEmpty(binding.retUser, true) -> false

            isPasswordInsecure() -> {
                activity?.snackBar(getString(R.string.signup__error_passwords_match),binding.RegisteredEmail)
                false
            }

            else -> true

        }
    }

    private fun isPasswordInsecure(): Boolean{
        return if (binding.repPassword1.text.toString().length <= 6){
            activity?.snackBar(getString(R.string.signup__error_password_insecure),binding.RegisteredEmail)
            true
        } else {
            binding.repPassword1.text.toString() != binding.repPassword2.text.toString()
        }
    }



    private fun manageRegisterErrorMessages(exception: Exception) {
        if (exception.toString() == EMAIL_ALREADY_EXISTS) {
            activity?.toast(getString(R.string.signup__error_email_already_registered))
        } else {
            activity?.toast(getString(R.string.signup__error_unknown_error))
        }
    }

    private fun hideProgressDialog() {
        binding.pbSignUp.visibility = View.GONE
        binding.RegisteredEmail.text = getString(R.string.signup__signup_button)
        binding.RegisteredEmail.isEnabled = true
    }

    private fun showProgressBar() {
        binding.RegisteredEmail.text = ""
        binding.RegisteredEmail.isEnabled = false
        binding.pbSignUp.visibility = View.VISIBLE
    }


}