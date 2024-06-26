package com.example.firewatch.presentation.components.dropDown

import android.content.Context
import android.content.res.TypedArray
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.findViewTreeLifecycleOwner
import com.example.firewatch.R
import com.example.firewatch.databinding.DropdownMenuBinding
import com.example.firewatch.shared.utils.SizeUtil

class DropDown @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val autoComplete: AutoCompleteTextView
    private var adapter: ArrayAdapter<String>? = null

    private val binding: DropdownMenuBinding =
        DropdownMenuBinding.inflate(LayoutInflater.from(context), this, true)

    private var onDropDownItemSelected: OnDropDownItemSelected? = null

    init {
        autoComplete = binding.autoCompleteTextView
        val hintText = binding.hintText

        attrs?.let {
            val attributes: TypedArray = context.obtainStyledAttributes(
                it, R.styleable.DropDown, 0, 0
            )

            val hint = attributes.getText(R.styleable.DropDown_hintText)
            hintText.text = hint

            if (context is LifecycleOwner) {
                val lifecycleOwner = context as LifecycleOwner
                SizeUtil.currentSize.observe(lifecycleOwner, Observer {
                    val layoutParams = autoComplete.layoutParams
                    val oi = SizeUtil.getCurrentComponentSize(context)
                    layoutParams.height = oi
                    autoComplete.layoutParams = layoutParams
                })
            }

            attributes.recycle()
        }

        autoComplete.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s == null) return

                if (s.isNotEmpty() || hintText.length() == 0) {
                    hintText.visibility = View.INVISIBLE
                } else {
                    hintText.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {
                onDropDownItemSelected?.onItemSelected(s.toString())
            }
        })
    }

    fun addOnDropDownItemSelected(l: OnDropDownItemSelected) {
        onDropDownItemSelected = l
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
