package com.example.firewatch.presentation.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityUpdateBurnBinding
import com.example.firewatch.presentation.views.burns.UpdateBurnOne
import com.example.firewatch.presentation.views.burns.UpdateBurnTwo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateBurn : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateBurnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_burn)
        val swiper = binding.updateBurnSwiper
        swiper.addPages(listOf(
            UpdateBurnOne::class.java,
            UpdateBurnTwo::class.java
        ))
    }
}