package com.example.firewatch.presentation.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityDetailBurnBinding
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.valueObjects.Address
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.presentation.viewModels.burns.DetailBurnViewModel
import com.example.firewatch.presentation.viewModels.burns.UpdateState
import com.example.firewatch.shared.helpers.DateHelper
import com.example.firewatch.shared.helpers.SwiperViews
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@AndroidEntryPoint
class DetailBurnActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDetailBurnBinding
    private val viewModel: DetailBurnViewModel by viewModels()

    companion object {
        private const val BURN_DETAIL_ID = "burn_detail_id"
        fun new(context: Context, id: String) {
            val intent = Intent(context, DetailBurnActivity::class.java).apply {
                putExtra(BURN_DETAIL_ID, id)
            }

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_burn)
        binding.viewModel = viewModel

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

        binding.editBurnBtn.setOnClickListener {
            viewModel.detailBurn.value?.let { burn ->
                EditDetailBurn.new(this, detailId, burn.state)
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun drawScreen(detailBurn: Burn) {
        val editBtn = binding.editBurnBtn
        val terminateBtn = binding.terminateBurnBtn

        binding.detailTitle.text = detailBurn.title
        binding.initDateTxt.text = setCreatedDate(detailBurn.beginAt)

        binding.detailStreet.text = detailBurn.address?.street
        binding.detailCity.text = detailBurn.address?.city
        binding.detailZipCode.text = detailBurn.address?.zipCode
        binding.detailLat.text = "Lat: ${detailBurn.coordinates.lat}"
        binding.detailLon.text = "Lot: ${detailBurn.coordinates.lon}"

        when (detailBurn.state) {
            BurnState.COMPLETED -> {
                editBtn.visibility = View.GONE
                terminateBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.reload))

                binding.terminateBurnBtn.setOnClickListener {
                    SwiperViews.updateBurn(this, detailBurn.id, UpdateState.REPEAT)
                }
            }
            BurnState.SCHEDULED -> {
                terminateBtn.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.check_correct))
                binding.terminateBurnBtn.setOnClickListener {
                    viewModel.start(detailBurn.id)
                }
            }
            else -> {
                editBtn.visibility = View.VISIBLE
                terminateBtn.visibility = View.VISIBLE

                binding.terminateBurnBtn.setOnClickListener {
                    viewModel.terminate(detailBurn.id)
                }
            }
        }
    }

    private fun setCreatedDate(date: LocalDateTime): String {
        return "${resources.getString(R.string.created_at)} ${DateHelper.getFormattedDate(date)}"
    }
}