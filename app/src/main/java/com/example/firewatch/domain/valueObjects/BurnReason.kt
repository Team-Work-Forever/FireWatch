package com.example.firewatch.domain.valueObjects

import android.content.Context
import com.example.firewatch.R

enum class BurnReason(val value: String, val description: Int) {
    SANITARY_BURN("sanitaryBurn", R.string.sanitary_burn),
    AGRITORAL_WASTE_MANAGEMENT("agritoralWasteManagement", R.string.agritoralwastemana),
    FORESTRY_WASTE_MANAGEMENT("forestryWasteManagement", R.string.forest_waste_management),
    BUSH_MANAGEMENT("bushManagement", R.string.bush_management),
    OTHERS("others", R.string.others);

    companion object {
        fun get(value: String): BurnReason? {
            return BurnReason.entries.find { it.value == value }
        }

        fun getFromDescription(description: Int): BurnReason? {
            return BurnReason.entries.find {
                return@find it.description == description
            }
        }

        fun getFromDescription(context: Context?, description: String): BurnReason? {
            return BurnReason.entries.find {
                val comparingString = context?.getString(it.description)

                return@find comparingString == description
            }
        }

        fun getName(context: Context?, reason: String): String {
            val id = BurnReason.entries.find { it.value == reason }?.description

            if (id == null) {
                return ""
            }

            return context?.getString(id) ?: ""
        }

        fun getValues(context: Context?): Array<String> {
            if (context == null) {
                return arrayOf()
            }

            return entries.map {
               getName(context, it.value)
            }.toTypedArray()
        }
    }
}