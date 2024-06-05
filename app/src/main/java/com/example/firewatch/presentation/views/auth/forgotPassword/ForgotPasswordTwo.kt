package com.example.firewatch.presentation.views.auth.forgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.databinding.FragmentForgotPasswordTwoBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.auth.ForgotPasswordViewModel
import com.example.firewatch.presentation.views.SwiperActivity
import com.example.firewatch.presentation.views.profile.UpdateProfileOne
import com.example.firewatch.presentation.views.profile.UpdateProfileTwo
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class ForgotPasswordTwo : Stage<ForgotPasswordViewModel>(ForgotPasswordViewModel::class.java) {
    private lateinit var binding: FragmentForgotPasswordTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordTwoBinding.inflate(inflater)
        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        header.setOnBackListener {
            back()
        }

        binding.continueBtn.setOnClickListener {
            SwiperActivity.create(requireActivity(), listOf(
                UpdateProfileOne::class.java,
                UpdateProfileTwo::class.java
            ))
        }

        return binding.root
    }
}