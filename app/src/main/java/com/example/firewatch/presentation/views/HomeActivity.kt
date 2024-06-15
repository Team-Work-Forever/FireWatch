package com.example.firewatch.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityHomeBinding
import com.example.firewatch.presentation.viewModels.home.HomeViewModel
import com.example.firewatch.presentation.views.views.home.ActiveBurns
import com.example.firewatch.presentation.views.views.home.HomeInsertBurn
import com.example.firewatch.presentation.views.views.home.Profile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BottomNavigationActivity(
    listOf(
        HomeInsertBurn::class.java,
        ActiveBurns::class.java,
        Profile::class.java
    )
) {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding

    companion object {
        fun new(context: Context) {
            val intent = Intent(context, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        super.onCreate(savedInstanceState)
        binding.viewModel = homeViewModel

        setMenuItemSelected {
            val id = when (it.itemId) {
                R.id.home_item -> 0
                R.id.bonfire_item -> 1
                R.id.person_item -> 2
                else -> 0
            }

            viewSlider.setCurrentItem(id, true)
            true
        }
    }
}