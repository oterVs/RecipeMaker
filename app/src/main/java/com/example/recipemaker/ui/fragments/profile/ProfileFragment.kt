package com.example.recipemaker.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.recipemaker.ui.activities.LogIn
import com.example.recipemaker.ui.activities.MainActivity
import com.example.recipemaker.R
import com.example.recipemaker.databinding.FragmentProfileBinding
import com.example.recipemaker.ui.rview.adapter.FoodAdapter

import com.example.recipemaker.domain.model.Recipe
import com.example.recipemaker.domain.model.User
import com.example.recipemaker.ui.fragments.session.DataStoreViewModel
import com.example.recipemaker.utils.Constants.USER_NOT_LOGGED
import com.example.recipemaker.utils.DataState
import com.example.recipemaker.utils.FoodProvider
import com.example.recipemaker.utils.toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
@AndroidEntryPoint
class ProfileFragment : Fragment() {


    private lateinit var binding : FragmentProfileBinding
    private val dataStore : DataStoreViewModel by activityViewModels()
    private val profileViewModel : ProfileViewModel by viewModels()
    private var listFood : MutableList<Recipe> = mutableListOf(Recipe())
    lateinit var adapter: FoodAdapter
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root

        //
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // println(dataStore.getUserName())
        initObservers()
        initListeners()
        initRecicleView()

        profileViewModel.userExist(dataStore.getUserName())
        profileViewModel.getFavoriteFood(FoodProvider.userLogger.recipes)

    }

    private fun initObservers() {
        profileViewModel.userExistD.observe(viewLifecycleOwner, Observer{
            when(it){
                is DataState.Success<User> -> {
                   // activity?.toast(it.data.email)
                    activity?.toast(dataStore.getStoreIsLogIn().toString())
                    FoodProvider.userLogger = it.data
                    binding.nameProfile.text = it.data.name
                    Picasso.get().load(it.data.photoUrl).into(binding.profileimg)

                }
                else -> Unit
            }
        })

        profileViewModel.favoriteFood.observe(viewLifecycleOwner, Observer {
            when(it){
                is DataState.Success<List<Recipe>> -> {
                    // activity?.toast(it.data.email)

                    listFood = it.data.toMutableList()
                    activity?.toast(it.data.size.toString())
                    adapter.setData(listFood)
                    adapter.notifyDataSetChanged()
                    hideProgressDialog()
                }
                is DataState.Error -> {
                    hideProgressDialog()
                    manageRegisterErrorMessages(it.exception)
                }
                is DataState.Loading ->{
                    showProgressBar()
                }
                else -> { }

            }
        })
    }

    private fun manageRegisterErrorMessages(exception: Exception) {
        activity?.toast("algo salio mal $exception")

    }

    private fun hideProgressDialog() {
        binding.pbProfile.visibility = View.GONE

    }
    private fun showProgressBar() {

        binding.pbProfile.visibility = View.VISIBLE
    }

    fun initRecicleView() {
        adapter = FoodAdapter(listFood, 320,360){
        }

        binding.rvProfile.adapter = adapter

        binding.rvProfile.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    //do something
                }
            }
        })
    }

    fun initListeners(){


        binding.tabsProfile.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                activity?.toast(tab?.text.toString())
                if(tab?.text.toString() == "My recipes"){
                    profileViewModel.getFavoriteFood(FoodProvider.userLogger.recipes)
                } else if (tab?.text.toString() == "Favorites"){
                    profileViewModel.getFavoriteFood(FoodProvider.userLogger.favorites)
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })

        binding.addRecipe.setOnClickListener {
            findNavController().navigate(R.id.addRecipeFragment)
        }


        auth = FirebaseAuth.getInstance()
        //binding.nameProfile.text = intent.getStringExtra("email")
        binding.profileLogOut.setOnClickListener{
            //auth.signOut()
            //startActivity(Intent(activity as MainActivity, MainActivity::class.java))
            dataStore.storeIsLogIn(false)
            dataStore.storeEmail(USER_NOT_LOGGED)

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googlesigninclient = GoogleSignIn.getClient(activity as LogIn,gso)
            googlesigninclient.signOut()
            startActivity(Intent(activity as LogIn, MainActivity::class.java))
            activity?.finish()
        }
    }


}