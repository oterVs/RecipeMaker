package com.example.recipemaker.ui.fragments.addfood

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.recipemaker.ui.rview.adapter.DetailAdapter
import com.example.recipemaker.databinding.FragmentAddRecipeBinding
import com.example.recipemaker.domain.model.Recipe
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import androidx.lifecycle.Observer
import com.example.recipemaker.R
import com.example.recipemaker.ui.activities.LogIn
import com.example.recipemaker.ui.fragments.search.RecicleRecipeViewModel
import com.example.recipemaker.ui.fragments.signup.SignUpViewModel
import com.example.recipemaker.utils.*
import java.util.*

@AndroidEntryPoint
class AddRecipeFragment : Fragment() {


    lateinit var binding : FragmentAddRecipeBinding
    lateinit var image : Uri
    private val stepsl : MutableList<String> = mutableListOf()
    private val ingredients : MutableList<String> = mutableListOf()


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

        binding = FragmentAddRecipeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initrv()
        initListeners()
        initObservers()

    }

    private fun initObservers() {
        //Observer para guardar la receta
        addModel.saveFoodState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<Boolean> -> {
                    FoodProvider.userLogger.recipes.add(id)
                    signupView.saveUser(FoodProvider.userLogger)
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
        //actualizamos las recetas del usuario, con la recientemente añadida
        signupView.saveUserState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success<Boolean> -> {
                    hideProgressDialog()
                    activity?.snackBar(getString(R.string.signup__signup_successfully),binding.firebase)
                    //foodViewModel.onCreate()
                    activity?.onBackPressed()
                }
                is DataState.Error -> {
                   hideProgressDialog()
                    manageLoginErrorMessages(dataState.exception)
                }
                is DataState.Loading ->{
                  //  showProgressBar()
                }
                else -> { }
            }
        })
    }

    private fun initrv() {
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

        //cargamos una imagen de la galeria a la receta que estamos incertando
        binding.imgAdd.setOnClickListener{
            //verificar permiso
            if (ContextCompat.checkSelfPermission(activity as LogIn, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // Request the permission
                activity?.snackBar("No se han concedido permisos de acceso",binding.firebase)
                ActivityCompat.requestPermissions(activity as LogIn,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    FoodProvider.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
            } else {
                // Permission has already been granted
                // Access the gallery
                uploadImg()
            }
        }
        //subimos la receta a firebase
        binding.firebase.setOnClickListener{
            uploadFirebase()
        }

        //regresamos al perfil
        binding.backProfile.setOnClickListener {
            activity?.onBackPressed()
        }

        //escuchamos el teclado del celular cada vez que presione enter, se añade un item a la lista de ingredientes
        binding.ingredientes.setOnEditorActionListener { v, keyCode, event ->
           // activity?.toast(KeyEvent.KEYCODE_ENTER.toString())
            if(event != null || keyCode == 0){
                ingredients.add(binding.ingredientes.text.toString())
                adaptari.notifyItemInserted(ingredients.size-1)
                binding.ingredientes.setText("")
                true
            } else {
                false
            }

        }
        //escuchamos el teclado del celular cada vez que presione enter, se añade un item a la lista de pasos
        binding.steps.setOnEditorActionListener { v, keyCode, event ->
          // activity?.toast(keyCode.toString())
            if(event != null || keyCode == 0 ){
                stepsl.add(binding.steps.text.toString())
                adapters.notifyItemInserted(stepsl.size-1)
                binding.steps.setText("")
                true
            } else {
                false
            }

        }

    }

    //guardar receta
    private fun uploadFirebase(){

        if(DataOk()){

            val formater = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            val filename = formater.format(now)
            val storageReference = FirebaseStorage.getInstance().getReference("images/$filename")

            storageReference.putFile(image)
                .addOnSuccessListener {

                    val recipe = Recipe(
                        title = binding.addName.text.toString(),
                        duration = binding.addDuration.text.toString(),
                        imageUrl = "https://firebasestorage.googleapis.com/v0/b/recipemaker-8e1b3.appspot.com/o/images%2F$filename?alt=media&token=76748857-6de5-4247-bf13-41dc3ab6f621",
                        ingredients = ingredients,
                        steps = stepsl
                    )
                    id = recipe.id
                    addModel.saveRecipe(recipe)
                }.addOnFailureListener{
                    activity?.toast("Algo salió mal")
                }
        }

    }

    //validamos los datos de la receta
    private fun DataOk(): Boolean {
        return when{
            requireActivity().isInputEmpty(binding.addName, true) -> {
                // activity?.toast(getString(R.string.login__error_enter_email))
                activity?.snackBar("Ingrese el nombre de la receta",binding.firebase)
                false
            }
            requireActivity().isInputEmpty(binding.addDuration, true) -> {
                activity?.snackBar("Ingrese la duración de la receta",binding.firebase)
                false
            }
            ingredients.size == 0 -> {
                activity?.snackBar("Ingrese al menos un ingrediente",binding.firebase)
                false
            }
            stepsl.size == 0 -> {
                activity?.snackBar("Ingrese al menos un paso",binding.firebase)
                false
            }
            !::image.isInitialized -> {
                activity?.snackBar("Seleccione una imagen",binding.firebase)
                false
            }
            else ->{
                true // El usuario mete todos los datos
            }
        }
    }

    //load a image from de gallery
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


    //manage loader an errrs

    private fun showProgressBar() {
       // binding.buttonLogin.text = ""
        binding.firebase.isEnabled = false
        binding.pbSignIn.visibility = View.VISIBLE
    }
    private fun manageLoginErrorMessages(exception: Exception) {
        when(exception.message){
            Constants.USER_NOT_EXISTS -> { activity?.snackBar(getString(R.string.login__error_user_no_registered),binding.firebase) }
            Constants.WRONG_PASSWORD -> { activity?.snackBar(getString(R.string.login__error_wrong_password),binding.firebase) }
            else -> { activity?.snackBar(getString(R.string.login__error_unknown_error),binding.firebase) }
        }
    }
    private fun hideProgressDialog() {
        binding.pbSignIn.visibility = View.GONE
        //binding.buttonLogin.text = getString(R.string.login__login_button)
        binding.firebase.isEnabled = true
    }

}