package com.example.recipemaker.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.recipemaker.R
import com.example.recipemaker.databinding.FragmentRegisteredBinding


class RegisteredFragment : Fragment() {


    private lateinit var binding : FragmentRegisteredBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisteredBinding.inflate(layoutInflater);
        binding.RegisteredEmail.setOnClickListener {
            findNavController().navigate(R.id.registerEmailFragment)
        }
        binding.backLogin.setOnClickListener{
            findNavController().navigate(R.id.fragmentLogIn)
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


}