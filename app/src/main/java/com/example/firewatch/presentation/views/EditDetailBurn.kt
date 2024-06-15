package com.example.firewatch.presentation.views

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityEditDetailBurnBinding
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.presentation.viewModels.burns.EditBurnDetailViewModel
import com.example.firewatch.presentation.views.burns.UpdateBurnOne
import com.example.firewatch.presentation.views.burns.UpdateBurnTwo
import com.example.firewatch.shared.helpers.SwiperViews
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditDetailBurn : AppCompatActivity() {
    private lateinit var binding: ActivityEditDetailBurnBinding
    private val viewModel: EditBurnDetailViewModel by viewModels()

    companion object {
        private const val BURN_ID = "burn_id"
        private const val BURN_STATE = "burn_state"

        fun new(context: Context, id: String, burnState: BurnState) {
            val intent = Intent(context, EditDetailBurn::class.java).apply {
                putExtra(BURN_ID, id)
                putExtra(BURN_STATE, burnState.state)
            }

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val id = intent.getStringExtra(BURN_ID) ?: throw Exception("Please provide an burn Id to this activity")
        val burnStateValue = intent.getStringExtra(BURN_STATE) ?: throw Exception("Please provide an burn state to this activity")
        val burnState = BurnState.get(burnStateValue)!!

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_detail_burn)

        if (burnState != BurnState.SCHEDULED) {
            binding.editBurnRemoveBtn.visibility = View.INVISIBLE
        }

        binding.editBurnBtn.setOnClickListener {
            SwiperViews.updateBurn(this, id)
        }

        binding.backBtn.setOnClickListener {
           finish()
        }

        binding.editBurnRemoveBtn.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Are you sure that you want to remove this burn?")
                .setPositiveButton("yes") { _, _ ->
                    viewModel.removeBurn(id)
                }
                .setNegativeButton("no", null)
                .create()
                .show()
        }
    }
}