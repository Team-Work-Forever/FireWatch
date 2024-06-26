package com.example.firewatch.presentation.views.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentUpdateProfileOneBinding
import com.example.firewatch.domain.entities.Autarchy
import com.example.firewatch.domain.entities.User
import com.example.firewatch.domain.valueObjects.UserType
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.components.dropDown.LanguageDropDownAdapter
import com.example.firewatch.presentation.components.dropDown.LanguageDropDownFilter
import com.example.firewatch.presentation.components.dropDown.OnDropDownItemSelected
import com.example.firewatch.presentation.components.textField.TextField
import com.example.firewatch.presentation.viewModels.profile.UpdateProfileViewModel
import com.example.firewatch.services.countries.CountryService
import com.example.firewatch.services.countries.CountryServiceImpl
import com.example.firewatch.shared.helpers.ImageHelper
import com.example.firewatch.shared.helpers.SwiperViews
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
@WithFragmentBindings
class UpdateProfileOne : Stage<UpdateProfileViewModel>(UpdateProfileViewModel::class.java) {
    private lateinit var binding: FragmentUpdateProfileOneBinding
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
        binding = FragmentUpdateProfileOneBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setUp()

        val swiperHeader = binding.swiperHeader
        swiperHeader.setTotalPage(totalPages)
        swiperHeader.setOnBackListener {
            exit()
        }

        binding.continueBtn.setOnClickListener {
            next()
        }

        binding.profileChangePasswordTxt.setOnClickListener {
           viewModel.authUser?.let {
               SwiperViews.forgotPassword(requireActivity(), it.email)
           }
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
            setValueOn(binding.updateProfilePhoneNumber, viewModel.phoneNumber, viewModel.authUser?.phone?.number)

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

    private fun setUp() {
        val userNameField = binding.updateProfileUserName

        when (viewModel.authUser?.userType) {
            UserType.AUTARCHY -> {
                val user: Autarchy = viewModel.authUser as Autarchy
                userNameField.setInputText(getString(R.string.update_profile_title))
                setValueOn(userNameField, viewModel.userName, user.title)
            }
            else -> {
                val user: User = viewModel.authUser as User

                setValueOn(userNameField, viewModel.userName, user.userName)
            }
        }

        setValueOn(binding.updateProfileNif, viewModel.nif, viewModel.authUser?.nif)

        ImageHelper.loadImage(viewModel.authUser?.avatar, binding.pickAvatar)
        viewModel.avatarFile.value = File("default")
        viewModel.phoneCode.value = "+351"

        binding.pickAvatar.setOnClickListener {
            pickAvatarResult.launch("image/*")
        }
    }
}