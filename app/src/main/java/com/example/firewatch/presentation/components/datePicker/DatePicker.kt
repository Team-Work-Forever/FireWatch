package com.example.firewatch.presentation.components.datePicker

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.firewatch.R
import com.example.firewatch.databinding.DatePickBinding
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDateTime
import java.util.Calendar

class DatePick @JvmOverloads constructor (
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private val binding: DatePickBinding =
        DatePickBinding.inflate(LayoutInflater.from(context), this, true)

    private val layout: TextInputLayout = binding.datePickLayout
    var dateError: CharSequence?
        get() = layout.error
        set(value) {
            layout.error = value
        }

    init {
        val dpLabel = binding.datePickerLabel

        attrs?.let {
            val attributes: TypedArray = context.obtainStyledAttributes(
                it, R.styleable.DatePick, 0, 0
            )

            val label = attributes.getText(R.styleable.DatePick_label)
            val error = attributes.getText(R.styleable.DatePick_dateError)

            dpLabel.text = label
            layout.error = error

            attributes.recycle()
        }
    }

    fun setOnDatePickClick(listener: DatePickerDialog.OnDateSetListener) {
        binding.datePickBtn.setOnClickListener {
            showDatePicker(listener)
        }
    }

    @SuppressLint("SetTextI18n")
    fun setValue(year : Int, month: Int, day: Int) {
        binding.datePickerLabel.text = "$day/$month/$year"
    }

    @SuppressLint("SetTextI18n")
    fun setValue(date: LocalDateTime) {
        binding.datePickerLabel.text = "${date.dayOfMonth}/${date.monthValue}/${date.year}"
    }
    private fun showDatePicker(listener: DatePickerDialog.OnDateSetListener) {
        val calendar: Calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            context,
            listener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }
}