package com.example.firewatch.shared.utils

import android.content.Context
import androidx.lifecycle.MutableLiveData

enum class ComponentSize(val delta: Int, val title: String) {
    Big(30, "Big"),
    Medium(20, "Medium"),
    Small(10, "Small")
    ;
}

object SizeUtil {
    private const val DEFAULT_COMPONENT_SIZE = 65
    val currentSize = MutableLiveData(ComponentSize.Big)

    private fun dpToPx(context: Context, dp: Int): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    fun getCurrentComponentSize(context: Context): Int {
        return dpToPx(context, DEFAULT_COMPONENT_SIZE + currentSize.value!!.delta)
    }
}