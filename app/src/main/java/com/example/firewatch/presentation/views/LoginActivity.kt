package com.example.firewatch.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.firewatch.MainActivity
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityLoginBinding
import com.example.firewatch.presentation.viewModels.auth.MainViewModel
import com.example.firewatch.shared.helpers.SwiperViews
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: MainViewModel by viewModels()

    companion object {
        fun new(context: Context) {
            val intent = Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
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