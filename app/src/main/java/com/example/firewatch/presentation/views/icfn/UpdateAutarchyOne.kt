package com.example.firewatch.presentation.views.icfn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentRegisterAutarchyOneBinding
import com.example.firewatch.databinding.FragmentUpdateAutarchyOneBinding
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.icfn.UpdateAutarchyViewModel
import com.example.firewatch.shared.helpers.ImageHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@AndroidEntryPoint
@WithFragmentBindings
class UpdateAutarchyOne : Stage<UpdateAutarchyViewModel>(UpdateAutarchyViewModel::class.java) {
    private lateinit var binding: FragmentUpdateAutarchyOneBinding
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
        binding = FragmentUpdateAutarchyOneBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val swiper = binding.swiperHeader
        swiper.setTotalPage(totalPages)

        viewLifecycleOwner.lifecycleScope.launch {
            if (!viewModel.getAutarchyId(UpdateAutarchyViewModel.id).await()) {
                Toast.makeText(requireActivity(), "There is no Autarchy with that id", Toast.LENGTH_LONG).show()

                return@launch exit()
            }

            withContext(Dispatchers.Main) {
                setUp()
            }
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

        return binding.root
    }

    private fun setUp() {
        setValueOn(binding.updateAutarchyNif, viewModel.nif, viewModel.autarchy.value?.nif)
        setValueOn(binding.updateAutarchyEmail, viewModel.email, viewModel.autarchy.value?.email)
        setValueOn(binding.updateAutarchyPhone, viewModel.phoneNumber, viewModel.autarchy.value?.phone?.number)
        setValueOn(binding.updateAutarchyName, viewModel.name, viewModel.autarchy.value?.title)

        ImageHelper.loadImage(viewModel.autarchy.value?.avatar, binding.pickAvatar)
        viewModel.avatarFile.value = File("default")

        binding.pickAvatar.setOnClickListener {
            pickAvatarResult.launch("image/*")
        }
    }
}