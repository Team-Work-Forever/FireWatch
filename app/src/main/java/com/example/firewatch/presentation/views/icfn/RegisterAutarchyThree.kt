package com.example.firewatch.presentation.views.icfn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentRegisterAutarchyThreeBinding
import com.example.firewatch.domain.valueObjects.Coordinates
import com.example.firewatch.presentation.adapters.Stage
import com.example.firewatch.presentation.viewModels.icfn.RegisterAutarchyViewModel
import com.example.firewatch.presentation.views.map.MapActivityResultContract
import com.example.firewatch.shared.extensions.getProblem
import kotlinx.coroutines.launch

class RegisterAutarchyThree : Stage<RegisterAutarchyViewModel>(RegisterAutarchyViewModel::class.java) {
    private lateinit var binding: FragmentRegisterAutarchyThreeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterAutarchyThreeBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this

        val swiper = binding.swiperHeader
        swiper.setTotalPage(totalPages)

        swiper.setOnBackListener {
            back()
        }

        val mapActivity = registerForActivityResult(MapActivityResultContract()) { params ->
            if (params == null) return@registerForActivityResult

            val coordinates = params.coordinates
            viewModel.coordinates.postValue(Coordinates.new(coordinates.lat, coordinates.lon))

            binding.registerAutarchyLat.text = coordinates.getLatDefinition()
            binding.registerAutarchyLon.text = coordinates.getLonDefinition()
        }

        viewModel.canStageThree.observe(viewLifecycleOwner, Observer {
            binding.continueBtn.isEnabled = it
        })

        binding.continueBtn.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val autarchy = viewModel.create().await()

                if (autarchy.isSuccess) {
                    return@launch exit()
                }

                Toast.makeText(requireActivity(), autarchy.getProblem(), Toast.LENGTH_LONG).show()
            }
        }

        binding.mapAction.setOnClickListener {
            mapActivity.launch("")
        }

        return binding.root
    }
}