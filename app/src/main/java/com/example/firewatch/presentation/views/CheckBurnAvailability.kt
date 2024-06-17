package com.example.firewatch.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityCheckBurnAvailabilityBinding
import com.example.firewatch.presentation.adapters.homeView.HomeView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckBurnAvailability : AppCompatActivity() {
    private lateinit var binding: ActivityCheckBurnAvailabilityBinding

    companion object {
        private const val CHECK_STATUS_PARAM = "checkStatus"

        fun new(activity: Context, status: Boolean) {
            val intent = Intent(activity, CheckBurnAvailability::class.java).apply {
                putExtra(CHECK_STATUS_PARAM, status)
            }

            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_burn_availability)
        val status = intent.getBooleanExtra(CHECK_STATUS_PARAM, false)

        if (status) {
            binding.pdfVisibility.visibility = View.VISIBLE
            binding.statusImg.setImageResource(R.drawable.check_correct)
            binding.statusTitle.text = getString(R.string.burn_is_good_to_go)
        }

        binding.comebackBtn.setOnClickListener {
             HomeActivity.new(this)
        }
    }
}