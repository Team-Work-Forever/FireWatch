package com.example.firewatch.presentation.components.datePicker

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener

object DatePickerBindingsAdapters  {
    @JvmStatic
    @BindingAdapter("dateError")
    fun setDateError(view: DatePick, value: String?) {
        if (view.dateError.toString() != value) {
            view.dateError = value ?: ""
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "dateError", event = "dateErrorAttrChanged")
    fun getDateError(view: DatePick): String {
        return view.dateError.toString()
    }

    @JvmStatic
    @BindingAdapter("dateErrorAttrChanged")
    fun setDateErrorListener(view: DatePick, listener: InverseBindingListener) {
    }
}
