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
import com.example.firewatch.presentation.views.SwiperActivity
import com.example.firewatch.presentation.views.auth.forgotPassword.ForgotPasswordOne
import com.example.firewatch.presentation.views.auth.forgotPassword.ForgotPasswordTwo
import com.example.firewatch.shared.helpers.ImageHelper
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
        binding.viewModel = viewModel

        setUp()

        val swiperHeader = binding.swiperHeader
        swiperHeader.setTotalPage(totalPages)
        swiperHeader.setOnBackListener {
            val intent = Intent(requireActivity(), HomeActivity::class.java);
            startActivity(intent);
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        binding.profileChangePasswordTxt.setOnClickListener {
            SwiperActivity.create(requireActivity(), listOf(
                ForgotPasswordOne::class.java,
                ForgotPasswordTwo::class.java
            ))
        }

        return binding.root
    }

    private fun setUp() {
        binding.updateProfileNif.setText(viewModel.authUser?.userName)
        binding.updateProfilePhoneNumber.setText(viewModel.authUser?.phone?.number.toString())
        binding.updateProfileUserName.setText(viewModel.authUser?.userName)

        ImageHelper.loadImage(viewModel.authUser?.avatar, binding.pickAvatar)
    }
}