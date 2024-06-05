package com.example.firewatch.presentation.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityRegisterBurnBinding
import com.example.firewatch.databinding.RegisterActivityBinding
import com.example.firewatch.presentation.views.burns.RegisterBurnOne
import com.example.firewatch.presentation.views.burns.RegisterBurnTwo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterBurn : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBurnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_burn)
        val swiper = binding.registerSwiper

        swiper.addPages(listOf(
            RegisterBurnOne::class.java,
            RegisterBurnTwo::class.java
        ))
    }
}