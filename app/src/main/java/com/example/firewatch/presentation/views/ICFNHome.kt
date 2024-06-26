package com.example.firewatch.presentation.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivityIcfnhomeBinding
import com.example.firewatch.presentation.views.views.icfn.ICFNAutarchies
import com.example.firewatch.presentation.views.views.icfn.ICFNAutarchiesBurns
import com.example.firewatch.presentation.views.views.icfn.ICFNRules
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ICFNHome : BottomNavigationActivity(listOf(
    ICFNRules::class.java,
    ICFNAutarchiesBurns::class.java,
    ICFNAutarchies::class.java
)){
   private lateinit var binding: ActivityIcfnhomeBinding

   companion object {
       fun new(context: Context) {
           val intent = Intent(context, ICFNHome::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
           }

           context.startActivity(intent)
       }
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_icfnhome)

        super.onCreate(savedInstanceState)

        setMenuItemSelected(mapOf(
            Pair(R.id.home_item, 0),
            Pair(R.id.bonfire_item, 1),
            Pair(R.id.autarchies, 2),
        ))
    }
}