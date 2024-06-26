package com.example.firewatch.presentation.views.icfn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.databinding.FragmentRegisterAutarchyOneBinding
import com.example.firewatch.databinding.FragmentRegisterBurnOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.components.dropDown.LanguageDropDownAdapter
import com.example.firewatch.presentation.components.dropDown.LanguageDropDownFilter
import com.example.firewatch.presentation.components.dropDown.OnDropDownItemSelected
import com.example.firewatch.presentation.viewModels.icfn.RegisterAutarchyViewModel
import com.example.firewatch.services.countries.CountryService
import com.example.firewatch.services.countries.CountryServiceImpl
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
@WithFragmentBindings
class RegisterAutarchyOne : Stage<RegisterAutarchyViewModel>(RegisterAutarchyViewModel::class.java) {
    private lateinit var binding: FragmentRegisterAutarchyOneBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterAutarchyOneBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val swiper = binding.swiperHeader
        swiper.setTotalPage(totalPages)

        binding.pickAvatar.setOnClickListener {
            pickAvatarResult.launch("image/*")
        }

        swiper.setOnBackListener {
            exit()
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        viewModel.canStageOne.observe(viewLifecycleOwner, Observer {
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
}