package com.example.firewatch.presentation.views.views.autarchies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.databinding.FragmentAutarchyActiveBurnsBinding
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.viewModels.autarchies.AutarchyActiveBurnsViewModel
import com.example.firewatch.presentation.views.Settings
import com.example.firewatch.shared.helpers.ImageHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class AutarchyActiveBurns : HomeView<AutarchyActiveBurnsViewModel>(AutarchyActiveBurnsViewModel::class.java) {
    private lateinit var binding: FragmentAutarchyActiveBurnsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAutarchyActiveBurnsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        ImageHelper.loadImage(viewModel.authUser?.avatar, binding.avatarPicture)

        binding.settingsBtn.setOnClickListener {
            val intent = Intent(requireContext(), Settings::class.java);
            startActivity(intent);
        }

        binding.totalBurns.text = viewModel.authUser?.totalBurns.toString()

        return binding.root
    }
}