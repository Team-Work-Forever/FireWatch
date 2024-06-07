package com.example.firewatch.presentation.views.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.FragmentActiveBurnsBinding
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.presentation.adapters.cardItem.CardItemAdapter
import com.example.firewatch.presentation.adapters.cardItem.CardItemDecoration
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.viewModels.home.ActiveBurnsViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch

@AndroidEntryPoint
@WithFragmentBindings
class ActiveBurns : HomeView<ActiveBurnsViewModel>(ActiveBurnsViewModel::class.java) {
    private lateinit var binding: FragmentActiveBurnsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActiveBurnsBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
              repeatOnLifecycle(Lifecycle.State.STARTED) {
                  viewModel.getBurns(
                      state = BurnState.ACTIVE
                  )
            }
        }

        val recyclerView: RecyclerView = binding.activeBurnsList
        val adapter = CardItemAdapter(requireActivity())
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(CardItemDecoration())

        viewModel.burns.observe(viewLifecycleOwner, Observer {
            adapter.setBurns(it)
        })

        viewModel.searchField.observe(viewLifecycleOwner, Observer {
            viewModel.getBurns(
                search = it,
                state = BurnState.ACTIVE
            )
        })

        return binding.root
    }
}