package com.example.firewatch.presentation.components.dropDown

import android.text.InputFilter
import android.text.Spanned

class DropDownFilter(
    val onConvert: (key: CharSequence) -> String
) : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence {
        if (source == null) {
            return ""
        }

        return onConvert(source)
    }
}