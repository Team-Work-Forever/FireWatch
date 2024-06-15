package com.example.firewatch.presentation.components.digitTextField

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object DigitBindingsAdapters  {
    @JvmStatic
    @BindingAdapter("digitError")
    fun setDateError(view: DigitTextField, value: String?) {
        if (view.digitError.toString() != value) {
            view.digitError = value ?: ""
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "digitError", event = "digitErrorAttrChanged")
    fun getDateError(view: DigitTextField): String {
        return view.digitError.toString()
    }

    @JvmStatic
    @BindingAdapter("digitErrorAttrChanged")
    fun setDateErrorListener(view: DigitTextField, listener: InverseBindingListener) {
    }
}
