package com.example.recipemaker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.recipemaker.databinding.FragmentRegisterEmailBinding
import com.example.recipemaker.databinding.FragmentRegisteredBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RegisterEmailFragment : Fragment() {

    lateinit var binding : FragmentRegisterEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterEmailBinding.inflate(inflater,container,false)
        binding.backRegistered.setOnClickListener{
            findNavController().navigate(R.id.registeredFragment)
        }
        return binding.root
    }


}