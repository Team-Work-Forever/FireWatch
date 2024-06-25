package com.example.firewatch.presentation.views.burns

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.databinding.FragmentRegisterBurnTwoBinding
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.burns.RegisterBurnViewModel
import com.example.firewatch.presentation.views.CheckBurnAvailability
import com.example.firewatch.presentation.views.map.MapActivityResultContract
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch

@AndroidEntryPoint
@WithFragmentBindings
class RegisterBurnTwo : Stage<RegisterBurnViewModel>(RegisterBurnViewModel::class.java) {
    private lateinit var binding: FragmentRegisterBurnTwoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBurnTwoBinding.inflate(layoutInflater)


        val header = binding.swiperHeader
        header.setTotalPage(totalPages)

        header.setOnBackListener {
            back()
        }

        binding.continueBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val checkStatus = viewModel.create().await()
                CheckBurnAvailability.new(requireActivity(), checkStatus)
            }
        }

        val mapActivity = registerForActivityResult(MapActivityResultContract()) { params ->
            if (params == null) return@registerForActivityResult

            val coordinates = params.coordinates
            viewModel.coordinates.postValue(Coordinates.new(coordinates.lat, coordinates.lon))

            binding.registerBurnLat.text = coordinates.getLatDefinition()
            binding.registerBurnLon.text = coordinates.getLonDefinition()
            viewModel.file.value = params.picture
        }

        binding.defineLocation.setOnClickListener {
            mapActivity.launch("")
        }

        viewModel.canStageTwo.observe(viewLifecycleOwner, Observer {
            binding.continueBtn.isEnabled = it
        })

        return binding.root
    }
}