package com.example.firewatch.presentation.views.views.icfn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.FragmentICFNAutarchiesBinding
import com.example.firewatch.presentation.adapters.cardItem.CardItemDecoration
import com.example.firewatch.presentation.adapters.completeAutarchyItem.CompleteAutarchyAdapter
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.viewModels.icfn.ICFNAutarchiesViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch

@AndroidEntryPoint
@WithFragmentBindings
class ICFNAutarchies : HomeView<ICFNAutarchiesViewModel>(ICFNAutarchiesViewModel::class.java) {
    private lateinit var binding: FragmentICFNAutarchiesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentICFNAutarchiesBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getAllAutarchies()
        }

        val recyclerView: RecyclerView = binding.activeBurnsList
        val adapter = CompleteAutarchyAdapter()
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(CardItemDecoration())

        viewModel.autarchies.observe(viewLifecycleOwner, Observer {
            adapter.setAutarchies(it)
        })

        viewModel.searchField.observe(viewLifecycleOwner, Observer {
            viewModel.getAllAutarchies(
                search = it,
            )
        })
        return binding.root
    }
}