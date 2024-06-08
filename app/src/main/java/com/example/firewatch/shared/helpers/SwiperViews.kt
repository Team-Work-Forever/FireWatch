package com.example.firewatch.shared.helpers

import android.content.Context
import com.example.firewatch.presentation.viewModels.auth.ForgotPasswordViewModel
import com.example.firewatch.presentation.viewModels.burns.UpdateBurnViewModel
import com.example.firewatch.presentation.views.SwiperActivity
import com.example.firewatch.presentation.views.auth.forgotPassword.ForgotPasswordOne
import com.example.firewatch.presentation.views.auth.forgotPassword.ForgotPasswordTwo
import com.example.firewatch.presentation.views.auth.stages.RegisterStageOne
import com.example.firewatch.presentation.views.auth.stages.RegisterStageThree
import com.example.firewatch.presentation.views.auth.stages.RegisterStageTwo
import com.example.firewatch.presentation.views.burns.UpdateBurnOne
import com.example.firewatch.presentation.views.burns.UpdateBurnTwo
import com.example.firewatch.presentation.views.profile.UpdateProfileOne
import com.example.firewatch.presentation.views.profile.UpdateProfileTwo

object SwiperViews {
    fun forgotPassword(context: Context, email: String) {
        ForgotPasswordViewModel.email = email

        return SwiperActivity.create(context, listOf(
            ForgotPasswordOne::class.java,
            ForgotPasswordTwo::class.java
        ))
    }

    fun registerUser(context: Context) {
        SwiperActivity.create(context, listOf(
            RegisterStageOne::class.java,
            RegisterStageTwo::class.java,
            RegisterStageThree::class.java
        ))
    }
    fun updateProfile(context: Context) {
        SwiperActivity.create(
            context, listOf(
                UpdateProfileOne::class.java,
                UpdateProfileTwo::class.java
            )
        )
    }

    fun updateBurn(context: Context, id: String) {
        UpdateBurnViewModel.id = id

        SwiperActivity.create(context, listOf(
            UpdateBurnOne::class.java,
            UpdateBurnTwo::class.java
        ))
    }}
