package com.example.firewatch.presentation.views.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentUpdateProfileTwoBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.profile.UpdateProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class UpdateProfileTwo : Stage<UpdateProfileViewModel>(UpdateProfileViewModel::class.java) {
    private lateinit var binding: FragmentUpdateProfileTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateProfileTwoBinding.inflate(layoutInflater)

        val swiperHeader = binding.swiperHeader
        swiperHeader.setTotalPage(totalPages)

        swiperHeader.setOnBackListener {
            back()
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        return binding.root
    }
}