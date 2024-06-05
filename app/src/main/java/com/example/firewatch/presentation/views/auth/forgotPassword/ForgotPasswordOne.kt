package com.example.firewatch.presentation.views.auth.forgotPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentForgotPasswordOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.auth.ForgotPasswordViewModel
import com.example.firewatch.presentation.views.SwiperActivity
import com.example.firewatch.presentation.views.profile.UpdateProfileOne
import com.example.firewatch.presentation.views.profile.UpdateProfileTwo
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class ForgotPasswordOne : Stage<ForgotPasswordViewModel>(ForgotPasswordViewModel::class.java) {
    private lateinit var binding: FragmentForgotPasswordOneBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordOneBinding.inflate(layoutInflater)
        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        header.setOnBackListener {
            SwiperActivity.create(requireActivity(), listOf(
                UpdateProfileOne::class.java,
                UpdateProfileTwo::class.java
            ))
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        return binding.root
    }
}