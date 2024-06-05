package com.example.firewatch.presentation.views.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.databinding.FragmentHomeInsertBurnBinding
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.viewModels.home.InsertBurnViewModel
import com.example.firewatch.presentation.views.RegisterBurn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class HomeInsertBurn : HomeView<InsertBurnViewModel>(InsertBurnViewModel::class.java) {
    private lateinit var binding: FragmentHomeInsertBurnBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeInsertBurnBinding.inflate(layoutInflater)

        binding.insertBurnBtn.setOnClickListener {
            val intent = Intent(requireActivity(), RegisterBurn::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}