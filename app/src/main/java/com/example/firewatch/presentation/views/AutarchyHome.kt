package com.example.firewatch.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityAutarchyHomeBinding
import com.example.firewatch.presentation.views.views.autarchies.AutarchyActiveBurns
import com.example.firewatch.presentation.views.views.autarchies.AutarchyBurnList
import com.example.firewatch.presentation.views.views.autarchies.AutarchyProfile
import com.example.firewatch.presentation.views.views.icfn.ICFNAutarchiesBurns
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AutarchyHome : BottomNavigationActivity(listOf(
    AutarchyActiveBurns::class.java,
    ICFNAutarchiesBurns::class.java,
    AutarchyProfile::class.java
)) {
    private lateinit var binding: ActivityAutarchyHomeBinding

     companion object {
        fun new(context: Context) {
            val intent = Intent(context, AutarchyHome::class.java).apply {
                flags = flags or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_autarchy_home)

        super.onCreate(savedInstanceState)

        setMenuItemSelected(mapOf(
            Pair(R.id.home_item, 0),
            Pair(R.id.bonfire_item, 1),
            Pair(R.id.person_item, 2),
        ))
    }
}