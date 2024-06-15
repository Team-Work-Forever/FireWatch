package com.example.firewatch.presentation.views.views.autarchies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentAutarchyBurnListBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.viewModels.autarchies.AutarchyBurnListViewModel
import com.example.firewatch.shared.helpers.ImageHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class AutarchyBurnList : HomeView<AutarchyBurnListViewModel>(AutarchyBurnListViewModel::class.java) {
    private lateinit var binding: FragmentAutarchyBurnListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAutarchyBurnListBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        ImageHelper.loadImage(viewModel.authUser?.avatar, binding.avatarPicture)

        return binding.root
    }
}