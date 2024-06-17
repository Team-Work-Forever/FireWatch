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
import com.example.firewatch.databinding.FragmentRegisterStageOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.components.textField.TextField
import com.example.firewatch.presentation.viewModels.auth.RegisterViewModel
import com.example.firewatch.shared.extensions.addValidators
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
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

        return binding.root
    }

    private fun setField(uiTextField: TextField, observer: MutableLiveData<String>, value: String) {
        uiTextField.setText(value)
        observer.postValue(value)
    }
}