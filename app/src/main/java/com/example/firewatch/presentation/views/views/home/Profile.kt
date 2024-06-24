package com.example.firewatch.presentation.views.views.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.FragmentProfileBinding
import com.example.firewatch.presentation.adapters.cardItem.CardItemAdapter
import com.example.firewatch.presentation.adapters.cardItem.CardItemDecoration
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.viewModels.burns.UpdateState
import com.example.firewatch.presentation.viewModels.home.ProfileViewModel
import com.example.firewatch.presentation.views.Settings
import com.example.firewatch.shared.helpers.ImageHelper
import com.example.firewatch.shared.helpers.SwiperViews
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch

class Profile : HomeView<ProfileViewModel>(ProfileViewModel::class.java) {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getLastBurns()
        }

        ImageHelper.loadImage(viewModel.authUser?.avatar, binding.avatarPicture)
        binding.profileNifTxt.text = setNif(viewModel.authUser?.userName)

        val recyclerView: RecyclerView = binding.profileLastList
        val adapter = CardItemAdapter(
            requireActivity(),
            bottomClick = { burn ->
                SwiperViews.updateBurn(requireActivity(), burn.id, UpdateState.REPEAT)
            }
        )
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(CardItemDecoration())

        binding.settingsBtn.setOnClickListener {
            val intent = Intent(requireContext(), Settings::class.java);
            startActivity(intent);
        }

        binding.updateProfileBtn.setOnClickListener {
           SwiperViews.updateProfile(requireActivity())
        }

        viewModel.burns.observe(viewLifecycleOwner, Observer { burn ->
            adapter.setBurns(burn)
        })

        return binding.root
    }

    private fun setNif(nif: String?): String = "NIF: ${ nif ?: "Not Defined" }"
}