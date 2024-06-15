package com.example.firewatch.presentation.components.dropDown

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.firewatch.R
import java.util.Locale

class LanguageDropDownAdapter(
    context: Context,
    private val countries: Map<String, String>
) : ArrayAdapter<String>(context, R.layout.dropdown, countries.keys.toTypedArray()) {
    fun getFormatedValue(countryCode: String): String {
        return "${toCountryFlag(countryCode)} \t ${countries.getValue(countryCode)}"
    }

    private fun toCountryFlag(countryCode: String): String {
       return countryCode
        .uppercase(Locale.US)
        .map { char ->
          Character.codePointAt("$char", 0) - 0x41 + 0x1F1E6
        }
        .map { codePoint ->
          Character.toChars(codePoint)
        }
        .joinToString(separator = "") { charArray ->
          String(charArray)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(R.id.drop_down_item)

        getItem(position)?.let {
            textView.text = getFormatedValue(it)
        }

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(R.id.drop_down_item)

        getItem(position)?.let {
            textView.text = getFormatedValue(it)
        }

        return view
    }
}