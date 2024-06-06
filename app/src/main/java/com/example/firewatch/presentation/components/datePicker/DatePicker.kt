package com.example.firewatch.presentation.components.datePicker

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.firewatch.R
import com.example.firewatch.databinding.DatePickBinding
import java.util.Calendar

class DatePick @JvmOverloads constructor (
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val binding: DatePickBinding =
        DatePickBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val dpLabel = binding.datePickerLabel

        attrs?.let {
            val attributes: TypedArray = context.obtainStyledAttributes(
                it, R.styleable.DatePick, 0, 0
            )

            val label = attributes.getText(R.styleable.DatePick_label)
            dpLabel.text = label

            attributes.recycle()
        }
    }

    fun setOnDatePickClick(listener: DatePickerDialog.OnDateSetListener) {
        binding.datePickBtn.setOnClickListener {
            showDatePicker(listener)
        }
    }

    private fun showDatePicker(listener: DatePickerDialog.OnDateSetListener) {
        val calendar: Calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            context, listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }
}