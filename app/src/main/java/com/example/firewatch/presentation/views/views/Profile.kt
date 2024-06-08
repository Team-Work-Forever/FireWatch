package com.example.firewatch.presentation.views.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.FragmentProfileBinding
import com.example.firewatch.presentation.adapters.cardItem.CardItemAdapter
import com.example.firewatch.presentation.adapters.cardItem.CardItemDecoration
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.viewModels.home.ProfileViewModel
import com.example.firewatch.presentation.views.Settings
import com.example.firewatch.presentation.views.SwiperActivity
import com.example.firewatch.presentation.views.profile.UpdateProfileOne
import com.example.firewatch.presentation.views.profile.UpdateProfileTwo
import com.example.firewatch.shared.helpers.ImageHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class Profile : HomeView<ProfileViewModel>(ProfileViewModel::class.java) {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        ImageHelper.loadImage(viewModel.authUser?.avatar, binding.avatarPicture)
        binding.profileNifTxt.text = setNif(viewModel.authUser?.userName)

        val recyclerView: RecyclerView = binding.profileLastList
        recyclerView.adapter = CardItemAdapter(requireActivity())
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(CardItemDecoration())

        binding.settingsBtn.setOnClickListener {
            val intent = Intent(requireContext(), Settings::class.java);
            startActivity(intent);
        }

        binding.updateProfileBtn.setOnClickListener {
            SwiperActivity.create(requireActivity(), listOf(
                UpdateProfileOne::class.java,
                UpdateProfileTwo::class.java
            ))
        }

        return binding.root
    }

    private fun setNif(nif: String?): String = "NIF: ${ nif ?: "Not Defined" }"
}