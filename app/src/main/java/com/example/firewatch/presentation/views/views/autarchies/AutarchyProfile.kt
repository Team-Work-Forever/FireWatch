package com.example.firewatch.presentation.views.views.autarchies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentAutarchyProfileBinding
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.viewModels.autarchies.AutarchyProfileViewModel
import com.example.firewatch.shared.helpers.ImageHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class AutarchyProfile : HomeView<AutarchyProfileViewModel>(AutarchyProfileViewModel::class.java) {
    private lateinit var binding: FragmentAutarchyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAutarchyProfileBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        ImageHelper.loadImage(viewModel.authUser?.avatar, binding.avatarPicture)

        return binding.root
    }
}