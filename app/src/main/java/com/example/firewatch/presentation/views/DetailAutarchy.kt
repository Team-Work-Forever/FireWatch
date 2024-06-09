package com.example.firewatch.presentation.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityDetailAutarchyBinding
import com.example.firewatch.presentation.viewModels.icfn.DetailAutarchyViewModel
import com.example.firewatch.shared.helpers.ImageHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailAutarchy : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAutarchyBinding
    private val viewModel: DetailAutarchyViewModel by viewModels()

    companion object {
        private const val AUTARCHY_ID = "autarchy_detail_id"

        fun new(context: Context, id: String) {
            val intent = Intent(context, DetailAutarchy::class.java).apply {
                putExtra(AUTARCHY_ID, id)
            }

            context.startActivity(intent)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_autarchy)
        binding.viewModel = viewModel

        val detailId = intent.getStringExtra(AUTARCHY_ID)
            ?: throw Exception("Please provide an id to instantiate this activity")

        lifecycleScope.launch {
            viewModel.getAutarchyById(detailId)
        }

        viewModel.autarchyDetail.observe(this, Observer { detailAutarchy ->
            if (detailAutarchy == null) return@Observer

            ImageHelper.loadImage(detailAutarchy.avatar, binding.avatarPicture)
            binding.detailAutarchiesPhone.text = "Contactos: ${detailAutarchy.phone.number}"
            binding.autarchyFullName.text = detailAutarchy.title
            binding.autarchyDetailEmail.text = detailAutarchy.email
            binding.detailAddress.text = detailAutarchy.address.toString()
            binding.detailLat.text = "Lat. ${detailAutarchy.coordinates.lat}"
            binding.detailLon.text = "Lon. ${detailAutarchy.coordinates.lon}"
        })

        binding.editBurnBtn.setOnClickListener {

        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}