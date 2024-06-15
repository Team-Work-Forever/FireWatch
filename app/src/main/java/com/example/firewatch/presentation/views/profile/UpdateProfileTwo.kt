package com.example.firewatch.presentation.views.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.databinding.FragmentUpdateProfileTwoBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.profile.UpdateProfileViewModel
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
        binding.lifecycleOwner = this

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

        viewModel.canStageTwo.observe(viewLifecycleOwner, Observer {
            binding.continueBtn.isEnabled = it
        })

        return binding.root
    }

    private fun setUp() {
        setValueOn(binding.updateProfileEmail, viewModel.email, viewModel.authUser?.email)
        setValueOn(binding.updateProfileStreet, viewModel.street, viewModel.authUser?.address?.street)
        setValueOn(binding.updateProfilePort, viewModel.streetNumber, viewModel.authUser?.address?.number.toString())
        setValueOn(binding.updateProfileZipCode, viewModel.zipCode, viewModel.authUser?.address?.zipCode)
        setValueOn(binding.updateProfileCity, viewModel.city, viewModel.authUser?.address?.city)
    }
}