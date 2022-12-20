package com.example.recipemaker.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.recipemaker.LogIn
import com.example.recipemaker.MainActivity
import com.example.recipemaker.R
import com.example.recipemaker.databinding.FragmentProfileBinding
import com.example.recipemaker.ui.login.LoginViewModel
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class ProfileFragment : Fragment() {


    private lateinit var binding : FragmentProfileBinding
    private val userViewModel : LoginViewModel by activityViewModels()

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
        setUp()
        return binding.root

        //
    }

    fun setUp(){
        println("*********************************")
        println("*********************************")
        println(userViewModel.userModel)
        println(userViewModel.userModel.value)
        //binding.nameProfile.text = userViewModel.userModel.toString()
        userViewModel.userModel.observe(activity as LogIn, Observer {

            println(it)
            binding.nameProfile.text = it
        })
        binding.tabsProfile.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                println(tab?.id)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })

        auth = FirebaseAuth.getInstance()
        //binding.nameProfile.text = intent.getStringExtra("email")
        binding.profileLogOut.setOnClickListener{
            auth.signOut()
            startActivity(Intent(activity as MainActivity, MainActivity::class.java))
        }
    }


}