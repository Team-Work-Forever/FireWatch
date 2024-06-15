package com.example.firewatch.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityAutarchyHomeBinding
import com.example.firewatch.presentation.views.views.autarchies.AutarchyBurnList
import com.example.firewatch.presentation.views.views.autarchies.AutarchyProfile
import com.example.firewatch.presentation.views.views.home.Profile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AutarchyHome :BottomNavigationActivity(
    listOf(
        AutarchyBurnList::class.java,
        AutarchyProfile::class.java
    )
) {
    private lateinit var binding: ActivityAutarchyHomeBinding

     companion object {
        fun new(context: Context) {
            val intent = Intent(context, AutarchyHome::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_autarchy_home)

        super.onCreate(savedInstanceState)

        setMenuItemSelected {
            val id = when (it.itemId) {
//                R.id.home_item -> 0
//                R.id.bonfire_item -> 1
                R.id.person_item -> 2
                else -> 0
            }

            viewSlider.setCurrentItem(id, true)
            true
        }
    }
}