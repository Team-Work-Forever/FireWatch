package com.example.firewatch.presentation.components.textField

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.example.firewatch.R
import com.google.android.material.textfield.TextInputEditText

object TextFieldBindingAdapters {
    @JvmStatic
    @BindingAdapter("text")
    fun setText(view: TextField, value: String?) {
        if (view.text.toString() != value) {
            view.text = value ?: ""
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "text", event = "textAttrChanged")
    fun getText(view: TextField): String {
        return view.text.toString()
    }

    @JvmStatic
    @BindingAdapter("textAttrChanged")
    fun setTextListener(view: TextField, listener: InverseBindingListener) {
        view.findViewById<TextInputEditText>(R.id.textInputEdit).addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                view.text = s
            }

            override fun afterTextChanged(s: Editable?) {
                listener.onChange()
            }
        })
    }

    @JvmStatic
    @BindingAdapter("input_type")
    fun setInputType(view: TextField, type: TextFieldType) {
        view.setType(type)
    }
}