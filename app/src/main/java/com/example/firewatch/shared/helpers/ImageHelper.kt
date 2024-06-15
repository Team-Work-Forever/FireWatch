package com.example.firewatch.shared.helpers

import android.widget.ImageView
import com.squareup.picasso.Picasso

object ImageHelper {
    fun loadImage(url: String?, on: ImageView) {
        Picasso.get().load(url).into(on)
    }
}