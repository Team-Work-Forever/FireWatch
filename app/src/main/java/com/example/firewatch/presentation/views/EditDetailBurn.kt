package com.example.firewatch.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityEditDetailBurnBinding
import com.example.firewatch.presentation.views.burns.RegisterBurnData
import com.example.firewatch.presentation.views.burns.UpdateBurnOne
import com.example.firewatch.presentation.views.burns.UpdateBurnTwo
import com.example.firewatch.presentation.views.profile.UpdateProfileOne
import com.example.firewatch.presentation.views.profile.UpdateProfileTwo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditDetailBurn : AppCompatActivity() {
    private lateinit var binding: ActivityEditDetailBurnBinding

    companion object {
        private const val BURN_ID = "burn_id"

        fun new(context: Context, id: String) {
            val intent = Intent(context, EditDetailBurn::class.java).apply {
                putExtra(BURN_ID, id)
            }

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val id = intent.getStringExtra(BURN_ID) ?: throw Exception("Please provide an burn Id to this actibity")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_detail_burn)

        binding.editBurnBtn.setOnClickListener {
            RegisterBurnData.id.postValue(id)
            SwiperActivity.create(this, listOf(
                UpdateBurnOne::class.java,
                UpdateBurnTwo::class.java
            ))
        }

        binding.backBtn.setOnClickListener {
           DetailBurnActivity.new(this, id)
        }
    }
}