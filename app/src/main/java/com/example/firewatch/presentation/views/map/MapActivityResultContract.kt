package com.example.firewatch.presentation.views.map

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.firewatch.domain.valueObjects.Coordinates
import java.math.BigDecimal
import java.math.RoundingMode

class MapActivityResultContract : ActivityResultContract<String, Coordinates?>() {
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, MapActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Coordinates? {
        if (resultCode != Activity.RESULT_OK || intent == null) {
            return null
        }

        val lat = intent.getDoubleExtra(MapActivity.LAT_RESULT, 0.0)
        val lon = intent.getDoubleExtra(MapActivity.LON_RESULT, 0.0)

        return Coordinates(
            BigDecimal.valueOf(lat).setScale(6, RoundingMode.HALF_UP),
            BigDecimal.valueOf(lon).setScale(6, RoundingMode.HALF_UP)
        )
    }
}