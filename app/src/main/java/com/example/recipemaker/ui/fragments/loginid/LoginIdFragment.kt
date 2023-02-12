package com.example.recipemaker.ui.fragments.loginid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.recipemaker.R
import com.example.recipemaker.databinding.FragmentLoginIdBinding
import com.example.recipemaker.utils.isInputEmpty
import com.example.recipemaker.utils.snackBar


class LoginIdFragment : Fragment() {

    private lateinit var binding : FragmentLoginIdBinding



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
        initListeners()
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

        }
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