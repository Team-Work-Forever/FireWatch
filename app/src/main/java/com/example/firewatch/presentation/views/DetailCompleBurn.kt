package com.example.firewatch.presentation.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityDetailCompleBurnBinding
import com.example.firewatch.shared.helpers.BaseActivity

class DetailCompleBurn : BaseActivity() {
    private lateinit var binding: ActivityDetailCompleBurnBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_comple_burn)
    }
}