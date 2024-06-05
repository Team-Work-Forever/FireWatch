package com.example.firewatch.presentation.components.dropDown

import android.content.Context
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import com.example.firewatch.databinding.DropdownMenuBinding

class DropDown @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val autoComplete: AutoCompleteTextView
    private var adapter: ArrayAdapter<String>? = null

    private val binding: DropdownMenuBinding =
        DropdownMenuBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        autoComplete = binding.autoCompleteTextView
    }

    fun setAdapter(adapter: ArrayAdapter<String>) {
        autoComplete.setAdapter(adapter)
        autoComplete.setText(adapter.getItem(0), false)

        this.adapter = adapter
    }

    fun setFilters(filters: Array<InputFilter>) {
        autoComplete.filters = filters
        adapter?.let {
            autoComplete.setText(it.getItem(0), false)
        }
    }
}
