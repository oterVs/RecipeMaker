package com.example.recipemaker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.recipemaker.adapter.DetailAdapter
import com.example.recipemaker.databinding.FragmentAddRecipeBinding
import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.ui.addeditfood.AddViewModel
import com.example.recipemaker.ui.detail.DetailViewModel
import com.example.recipemaker.utils.toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import java.net.URI
import java.text.SimpleDateFormat
import androidx.lifecycle.Observer
import com.example.recipemaker.ui.login.SignUpViewModel
import com.example.recipemaker.utils.DataState
import com.example.recipemaker.utils.FoodProvider
import java.util.*

@AndroidEntryPoint
class AddRecipeFragment : Fragment() {


    lateinit var binding : FragmentAddRecipeBinding
    lateinit var image : Uri
    private val stepsl : MutableList<String> = mutableListOf("1")
    private val ingredients : MutableList<String> = mutableListOf("1", "calentar la comida", "hervir el agua")

    private lateinit var adapters : DetailAdapter
    private lateinit var adaptari : DetailAdapter

    var id = ""

    private val addModel : AddViewModel by viewModels()
    private val signupView : SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddRecipeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initListeners()
        initObservers()

    }

    private fun initObservers() {
        addModel.saveFoodState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<Boolean> -> {
                    FoodProvider.userLogger.recipes.add(id)
                    signupView.saveUser(FoodProvider.userLogger)
                }
                is DataState.Error -> {
                    //hideProgressDialog()
                    //manageLoginErrorMessages(dataState.exception)
                }
                is DataState.Loading ->{
                    //showProgressBar()
                }
                else -> Unit
            }
        })
        signupView.saveUserState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<Boolean> -> {
                    activity?.toast(getString(R.string.signup__signup_successfully))
                    activity?.onBackPressed()
                }
                is DataState.Error -> {
                   // hideProgressDialog()
                   // manageRegisterErrorMessages(dataState.exception)
                }
                is DataState.Loading ->{
                  //  showProgressBar()
                }
                else -> { }
            }
        })
    }

    private fun init() {
        adapters = DetailAdapter(stepsl){
            stepsl.removeAt(it)
            adapters.notifyItemRemoved(it)
        }
        adaptari = DetailAdapter(ingredients){
            ingredients.removeAt(it)
            adaptari.notifyItemRemoved(it)
        }
        binding.rvaIngredientes.adapter = adaptari
        binding.rvaSteps.adapter = adapters
    }

    private fun initListeners() {

        binding.imgAdd.setOnClickListener{
           uploadImg()
        }

        binding.firebase.setOnClickListener{
            uploadFirebase()
        }

        binding.ingredientes.setOnEditorActionListener { v, keyCode, event ->
           // activity?.toast(KeyEvent.KEYCODE_ENTER.toString())
            if(keyCode == 0){
                ingredients.add(binding.ingredientes.text.toString())
                adaptari.notifyItemInserted(ingredients.size)
                binding.ingredientes.setText("")
                true
            } else {
                false
            }

        }
        binding.steps.setOnEditorActionListener { v, keyCode, event ->
          // activity?.toast(keyCode.toString())
            if(keyCode == 0 ){
                stepsl.add(binding.ingredientes.text.toString())
                adapters.notifyItemInserted(stepsl.size)
                binding.ingredientes.setText("")
                true
            } else {
                false
            }

        }

    }


    private fun uploadFirebase(){
        val formater = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val filename = formater.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("images/$filename")

        //val gsReference =  FirebaseStorage.getInstance().getReferenceFromUrl("gs://bucket/images/stars.jpg")
/*
        storageReference.downloadUrl
            .addOnSuccessListener {

            }.addOnFailureListener{

            }*/

        storageReference.putFile(image)
            .addOnSuccessListener {
                activity?.toast("exito")
                val recipe = Recipe(
                    title = binding.addName.toString(),
                    duration = binding.addDuration.toString(),
                    imageUrl = "images/$filename",
                    ingredients = ingredients,
                    steps = stepsl
                )
                id = recipe.id
                addModel.saveRecipe(recipe)
            }.addOnFailureListener{
                activity?.toast("fail")
            }
    }

    private fun uploadImg() {
        var intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1) {
            image = data?.data!!
            binding.imgAdd.setImageURI(image)
        }
    }

}