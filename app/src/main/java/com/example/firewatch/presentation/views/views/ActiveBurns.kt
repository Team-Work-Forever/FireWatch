package com.example.firewatch.presentation.views.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.FragmentActiveBurnsBinding
import com.example.firewatch.presentation.adapters.cardItem.CardItemAdapter
import com.example.firewatch.presentation.adapters.cardItem.CardItemDecoration
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.viewModels.ActiveBurnsViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
@WithFragmentBindings
class ActiveBurns : HomeView<ActiveBurnsViewModel>(ActiveBurnsViewModel::class.java) {
    private lateinit var binding: FragmentActiveBurnsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActiveBurnsBinding.inflate(layoutInflater)
        val recyclerView: RecyclerView = binding.activeBurnsList
        recyclerView.adapter = CardItemAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(CardItemDecoration())

        return binding.root
    }
}