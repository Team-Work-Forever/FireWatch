package com.example.firewatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.firewatch.databinding.ActivityMainBinding
import com.example.firewatch.presentation.viewModels.auth.MainViewModel
import com.example.firewatch.presentation.views.HomeActivity
import com.example.firewatch.presentation.views.SwiperActivity
import com.example.firewatch.presentation.views.auth.stages.RegisterStageOne
import com.example.firewatch.presentation.views.auth.stages.RegisterStageThree
import com.example.firewatch.presentation.views.auth.stages.RegisterStageTwo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.registerAccountLink.setOnClickListener {
            SwiperActivity.create(this, listOf(
                RegisterStageOne::class.java,
                RegisterStageTwo::class.java,
                RegisterStageThree::class.java
            ))
        }

//        val intent = Intent(this, HomeActivity::class.java);
//        startActivity(intent);
    }
}


