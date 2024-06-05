package com.example.firewatch.presentation.views.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.databinding.FragmentUpdateProfileOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.profile.UpdateProfileViewModel
import com.example.firewatch.presentation.views.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class UpdateProfileOne : Stage<UpdateProfileViewModel>(UpdateProfileViewModel::class.java) {
    private lateinit var binding: FragmentUpdateProfileOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateProfileOneBinding.inflate(layoutInflater)
        val swiperHeader = binding.swiperHeader
        swiperHeader.setTotalPage(totalPages)

        swiperHeader.setOnBackListener {
            val intent = Intent(requireActivity(), HomeActivity::class.java);
            startActivity(intent);
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        return binding.root
    }
}