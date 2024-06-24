package com.example.firewatch.presentation.views.map

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.example.firewatch.domain.valueObjects.Coordinates
import java.io.File
import java.math.BigDecimal
import java.math.RoundingMode

class MapParams(val coordinates: Coordinates, val picture: File)

class MapActivityResultContract : ActivityResultContract<String, MapParams?>() {
    override fun createIntent(context: Context, input: String): Intent {
        return Intent(context, MapActivity::class.java)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): MapParams? {
        if (resultCode != Activity.RESULT_OK || intent == null) {
            return null
        }

        val lat = intent.getDoubleExtra(MapActivity.LAT_RESULT, 0.0)
        val lon = intent.getDoubleExtra(MapActivity.LON_RESULT, 0.0)

        val file = intent.getSerializableExtra(MapActivity.PICTURE_RESULT) as File
        file.createNewFile()

        return MapParams(
            Coordinates(
                BigDecimal.valueOf(lat).setScale(6, RoundingMode.HALF_UP),
                BigDecimal.valueOf(lon).setScale(6, RoundingMode.HALF_UP)
            ),
            file
        )
    }
}