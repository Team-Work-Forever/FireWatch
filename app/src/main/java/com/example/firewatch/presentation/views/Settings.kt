package com.example.firewatch.presentation.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.firewatch.R
import com.example.firewatch.databinding.ActivitySettingsBinding
import com.example.firewatch.databinding.DropdownBinding
import com.example.firewatch.presentation.adapters.dropdown.LanguageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Settings : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var listItem: DropdownBinding

    private val options: Array<String> = arrayOf(
        "Option 1",
        "Option 2"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        val languageDropDown = binding.languagesDrop
        val dropDownAdapter = LanguageAdapter<String>(this, options)

        languageDropDown.setAdapter(dropDownAdapter)

        binding.backBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java);
            startActivity(intent);
        }
    }
}