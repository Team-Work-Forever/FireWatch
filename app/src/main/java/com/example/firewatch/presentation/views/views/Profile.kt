package com.example.firewatch.presentation.views.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.R
import com.example.firewatch.databinding.FragmentProfileBinding
import com.example.firewatch.presentation.adapters.cardItem.CardItemAdapter
import com.example.firewatch.presentation.adapters.cardItem.CardItemDecoration
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.viewModels.home.ProfileViewModel
import com.example.firewatch.presentation.views.Settings
import com.example.firewatch.presentation.views.UpdateProfile

class Profile : HomeView<ProfileViewModel>(ProfileViewModel::class.java) {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        val recyclerView: RecyclerView = binding.profileLastList
        recyclerView.adapter = CardItemAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(CardItemDecoration())

        binding.settingsBtn.setOnClickListener {
            val intent = Intent(requireContext(), Settings::class.java);
            startActivity(intent);
        }

        binding.updateProfileBtn.setOnClickListener {
            val intent = Intent(requireContext(), UpdateProfile::class.java);
            startActivity(intent);
        }

        return binding.root
    }
}