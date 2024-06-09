package com.example.firewatch.presentation.views.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.databinding.FragmentUpdateProfileTwoBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.profile.UpdateProfileViewModel
import com.example.firewatch.presentation.views.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch

@AndroidEntryPoint
@WithFragmentBindings
class UpdateProfileTwo : Stage<UpdateProfileViewModel>(UpdateProfileViewModel::class.java) {
    private lateinit var binding: FragmentUpdateProfileTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateProfileTwoBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        setUp()

        val swiperHeader = binding.swiperHeader
        swiperHeader.setTotalPage(totalPages)

        swiperHeader.setOnBackListener {
            back()
        }

        binding.continueBtn.setOnClickListener {
            lifecycleScope.launch {
                if (viewModel.updateProfile().await()) {
                    exit()
                }
            }
        }

        return binding.root
    }

    private fun setUp() {
        binding.updateProfileEmail.setText(viewModel.authUser?.email)
        binding.updateProfileStreet.setText(viewModel.authUser?.address?.street)
        binding.updateProfilePort.setText(viewModel.authUser?.address?.number.toString())
        binding.updateProfileZipCode.setText(viewModel.authUser?.address?.zipCode)
        binding.updateProfileCity.setText(viewModel.authUser?.address?.city)
    }
}