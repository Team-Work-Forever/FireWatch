package com.example.firewatch.presentation.components.dropDown

import android.text.InputFilter
import android.text.Spanned


class LanguageDropDownFilter(val adapter: LanguageDropDownAdapter) : InputFilter {
    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence {
        source?.let {
            return adapter.getFormatedValue(it.toString())
        }

        return ""
    }
}