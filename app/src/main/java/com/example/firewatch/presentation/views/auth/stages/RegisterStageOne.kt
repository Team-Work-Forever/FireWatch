package com.example.firewatch.presentation.views.auth.stages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.databinding.FragmentRegisterStageOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.components.dropDown.LanguageDropDownAdapter
import com.example.firewatch.presentation.components.dropDown.LanguageDropDownFilter
import com.example.firewatch.presentation.components.dropDown.OnDropDownItemSelected
import com.example.firewatch.presentation.components.textField.TextField
import com.example.firewatch.presentation.viewModels.auth.RegisterViewModel
import com.example.firewatch.services.countries.CountryService
import com.example.firewatch.services.countries.CountryServiceImpl
import com.example.firewatch.services.locales.Language
import com.example.firewatch.services.store.options.LanguageStore
import com.example.firewatch.shared.extensions.addValidators
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
@WithFragmentBindings
class RegisterStageOne : Stage<RegisterViewModel>(RegisterViewModel::class.java) {
    private lateinit var binding: FragmentRegisterStageOneBinding
    private var pickAvatarResult = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.pickAvatar.setImageURI(it)

        if (it == null) return@registerForActivityResult

        val file = File(requireActivity().cacheDir, "profile.png")
        file.createNewFile()

        requireActivity().contentResolver.openInputStream(it)?.use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }

       viewModel.avatarFile.postValue(file)
    }


    private lateinit var canStageOneComplete: LiveData<Boolean>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        canStageOneComplete = MediatorLiveData<Boolean>().apply {
            addValidators(listOf(
                viewModel.userNameValidator,
                viewModel.firstNameValidator,
                viewModel.lastNameValidator,
                viewModel.phoneValidator,
                viewModel.avatarValidator
            ))
        }

        binding = FragmentRegisterStageOneBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        binding.pickAvatar.setOnClickListener {
            pickAvatarResult.launch("image/*")
        }

        header.setOnBackListener {
            exit()
        }

        val observerFields = listOf(binding.firstNameField, binding.secondNameField)
        val viewModels = listOf(viewModel.firstName, viewModel.lastName)

        viewModel.userName.observe(viewLifecycleOwner, Observer { username ->
            val trimmedUsername = username.trim()
            val names = trimmedUsername.split(" ")

            if (observerFields.size < names.size) {
                return@Observer
            }

            for (i in names.indices) {
                val index = i % observerFields.size

                val observerField = observerFields[index]
                val viewModel = viewModels[index]

                setField(observerField, viewModel, names[i])
            }
        })

        val button = binding.continueBtn
        button.setOnClickListener {
            next()
        }

        canStageOneComplete.observe(viewLifecycleOwner, Observer {
            binding.continueBtn.isEnabled = it
        })

        lifecycleScope.launch {
            val countryService: CountryService = CountryServiceImpl
            val data = countryService.getCountries()

            val countriesDropDown = binding.countriesDropDown
            val languageAdapter = LanguageDropDownAdapter(requireContext(), data)
            countriesDropDown.setAdapter(languageAdapter)
            countriesDropDown.setFilters(arrayOf(LanguageDropDownFilter(languageAdapter)))
            viewModel.phoneCode.value = data.values.first()

            countriesDropDown.addOnDropDownItemSelected(object : OnDropDownItemSelected {
                override fun onItemSelected(item: String) {
                    val language = item.split(" \t ")

                    if (language.size != 2) {
                        return
                    }

                    val phoneCode = language[1]
                    viewModel.phoneCode.value = phoneCode
                }
            })
        }

        return binding.root
    }

    private fun setField(uiTextField: TextField, observer: MutableLiveData<String>, value: String) {
        uiTextField.setText(value)
        observer.postValue(value)
    }
}