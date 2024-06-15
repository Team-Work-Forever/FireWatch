package com.example.firewatch.presentation.views.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.databinding.FragmentHomeInsertBurnBinding
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.viewModels.home.InsertBurnViewModel
import com.example.firewatch.presentation.views.SwiperActivity
import com.example.firewatch.presentation.views.burns.RegisterBurnOne
import com.example.firewatch.presentation.views.burns.RegisterBurnTwo
import com.example.firewatch.shared.helpers.ImageHelper
import com.example.firewatch.shared.helpers.SwiperViews
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
        binding.viewModel = viewModel

        ImageHelper.loadImage(viewModel.authUser?.avatar, binding.avatarPicture)

        binding.insertBurnBtn.setOnClickListener {
            SwiperViews.createBurn(requireActivity())
        }

        return binding.root
    }
}