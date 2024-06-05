package com.example.firewatch.presentation.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityEditDetailBurnBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditDetailBurn : AppCompatActivity() {
    private lateinit var binding: ActivityEditDetailBurnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_detail_burn)

        binding.editBurnBtn.setOnClickListener {
            val intent = Intent(this, UpdateBurn::class.java)
            startActivity(intent)
        }

        binding.backBtn.setOnClickListener {
            val intent = Intent(this, DetailBurnActivity::class.java)
            startActivity(intent)
        }
    }
}