package com.example.firewatch.presentation.views.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.RegisterActivityBinding
import com.example.firewatch.presentation.views.auth.stages.RegisterStageOne
import com.example.firewatch.presentation.views.auth.stages.RegisterStageThree
import com.example.firewatch.presentation.views.auth.stages.RegisterStageTwo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: RegisterActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.register_activity)

        val swiper = binding.registerSwiper
        swiper.addPages(listOf(
            RegisterStageOne::class.java,
            RegisterStageTwo::class.java,
            RegisterStageThree::class.java
        ))
    }
}