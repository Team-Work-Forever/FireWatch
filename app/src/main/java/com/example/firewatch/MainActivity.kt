package com.example.firewatch

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.firewatch.databinding.ActivityMainBinding
import com.example.firewatch.presentation.viewModels.auth.MainViewModel
import com.example.firewatch.shared.helpers.SwiperViews
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    companion object {
        fun new(context: Context) {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.registerAccountLink.setOnClickListener {
            SwiperViews.registerUser(this)
        }

        binding.forgotPasswordLink.setOnClickListener {
            val email = viewModel.email.value

            if (email == null) {
                run {
                    Toast.makeText(this, "Please provide an valid email", Toast.LENGTH_LONG).show()
                }

                return@setOnClickListener
            }

            SwiperViews.forgotPassword(this, email)
        }
    }
}


