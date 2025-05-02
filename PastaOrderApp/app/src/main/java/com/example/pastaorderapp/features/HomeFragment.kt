package com.example.pastaorderapp.features

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.pastaorderapp.R
import com.example.pastaorderapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.startOffer.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_chooseTypeFragment)
        }

        return binding.root
    }
}