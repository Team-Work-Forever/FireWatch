package com.example.firewatch.presentation.views.burns

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityDetailBurnBinding
import com.example.firewatch.presentation.views.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailBurnActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBurnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_burn)

        binding.backBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}