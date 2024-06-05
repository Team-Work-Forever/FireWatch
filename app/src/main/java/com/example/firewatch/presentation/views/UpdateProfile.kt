package com.example.firewatch.presentation.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityUpdateProfileBinding
import com.example.firewatch.presentation.views.auth.stages.RegisterStageOne
import com.example.firewatch.presentation.views.auth.stages.RegisterStageThree
import com.example.firewatch.presentation.views.auth.stages.RegisterStageTwo
import com.example.firewatch.presentation.views.profile.UpdateProfileOne
import com.example.firewatch.presentation.views.profile.UpdateProfileTwo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateProfile : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_profile)

        val swiper = binding.updateProfileSwiper
        swiper.addPages(listOf(
            UpdateProfileOne::class.java,
            UpdateProfileTwo::class.java
        ))
    }
}