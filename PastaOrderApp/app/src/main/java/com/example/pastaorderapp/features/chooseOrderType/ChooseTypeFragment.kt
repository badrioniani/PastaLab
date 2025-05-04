package com.example.pastaorderapp.features.chooseOrderType

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.pastaorderapp.R
import com.example.pastaorderapp.databinding.FragmentChooseTypeBinding
import com.example.pastaorderapp.databinding.FragmentHomeBinding

class ChooseTypeFragment : Fragment(R.layout.fragment_choose_type) {

    private val binding by lazy {
        FragmentChooseTypeBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideSystemUI()
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //Empty to prevent default back press action
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.adgilze.setOnClickListener {
            findNavController().navigate(R.id.action_chooseTypeFragment_to_chooseOrderFragment)
        }
        binding.wasaghebad.setOnClickListener {
            findNavController().navigate(R.id.action_chooseTypeFragment_to_chooseOrderFragment)
        }
        return binding.root
    }
    private fun hideSystemUI() {
        activity?.window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )

    }
}