package com.example.firewatch.presentation.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityDetailCompleBurnBinding
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.presentation.viewModels.burns.DetailCompleteBurnViewModel
import com.example.firewatch.shared.helpers.BaseActivity
import com.example.firewatch.shared.helpers.ImageHelper
import com.example.firewatch.shared.utils.DateUtils
import kotlinx.coroutines.launch

class DetailCompleBurn : BaseActivity() {
    private lateinit var binding: ActivityDetailCompleBurnBinding
    private val viewModel: DetailCompleteBurnViewModel by viewModels()

    companion object {
        private const val BURN_DETAIL_ID = "burn_detail_id"
        fun new(context: Context, id: String) {
            val intent = Intent(context, DetailCompleBurn::class.java).apply {
                putExtra(BURN_DETAIL_ID, id)
            }

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_comple_burn)
        binding.lifecycleOwner = this

        val detailId = intent.getStringExtra(BURN_DETAIL_ID)
            ?: throw Exception("Please provide an id to instantiate this activity")

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getDetails(detailId)
            }
        }

        viewModel.detailBurn.observe(this, Observer { detailBurn ->
            if (detailBurn == null) return@Observer

            drawScreen(detailBurn)
        })

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun drawScreen(detailBurn: Burn) {
        binding.initDateTxt.text = "${resources.getString(R.string.created_at)} ${DateUtils.toString(detailBurn.beginAt)}"

        binding.street.text = detailBurn.address?.street
        binding.postalCode.text = detailBurn.address?.zipCode
        binding.city.text = detailBurn.address?.city

        binding.lat.text = "Lat: ${detailBurn.coordinates.lat}"
        binding.lon.text = "Lon: ${detailBurn.coordinates.lon}"

        binding.username.text = detailBurn.publicProfile?.userName
        binding.email.text = detailBurn.publicProfile?.email
        binding.nif.text = detailBurn.publicProfile?.nif
        binding.phone.text = detailBurn.publicProfile?.phone?.number.toString()

        ImageHelper.loadImage(detailBurn.publicProfile?.avatar, binding.avatarPicture)
    }
}