package com.example.firewatch.domain.valueObjects

import android.content.Context
import com.example.firewatch.R

enum class BurnType(val value: String, val description: Int) {
    BURN("burn", R.string.burn);

    companion object {
        fun get(value: String): BurnType? {
            return BurnType.entries.find { it.value == value }
        }

        fun getName(context: Context, reason: String): String {
            val id = BurnType.entries.find { it.value == reason }?.description

            if (id == null) {
                return ""
            }

            return context.getString(id)
        }

        fun getFromDescription(context: Context?, description: String): BurnType? {
            return BurnType.entries.find {
                val comparingString = context?.getString(it.description)

                return@find comparingString == description
            }
        }

        fun getValues(context: Context?): Array<String> {
            if (context == null) {
                return arrayOf()
            }

            return BurnType.entries.map {
                BurnType.getName(context, it.value)
            }.toTypedArray()
        }
    }
}