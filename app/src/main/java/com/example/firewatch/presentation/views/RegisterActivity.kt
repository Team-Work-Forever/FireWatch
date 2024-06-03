package com.example.firewatch.presentation.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.RegisterActivityBinding
import com.example.firewatch.presentation.components.stageSwiper.Swiper
import com.example.firewatch.presentation.views.stages.RegisterStageOne
import com.example.firewatch.presentation.views.stages.RegisterStageThree
import com.example.firewatch.presentation.views.stages.RegisterStageTwo

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