package com.example.firewatch.presentation.views.views.icfn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.FragmentICFNRulesBinding
import com.example.firewatch.presentation.adapters.autarchyItem.AutarchyItemAdapter
import com.example.firewatch.presentation.adapters.cardItem.CardItemDecoration
import com.example.firewatch.presentation.adapters.homeView.HomeView
import com.example.firewatch.presentation.viewModels.icfn.ICFNRulesViewModel
import com.example.firewatch.presentation.views.Settings
import com.example.firewatch.shared.helpers.ImageHelper
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import kotlinx.coroutines.launch

@AndroidEntryPoint
@WithFragmentBindings
class ICFNRules : HomeView<ICFNRulesViewModel>(ICFNRulesViewModel::class.java) {
    private lateinit var binding: FragmentICFNRulesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentICFNRulesBinding.inflate(layoutInflater)
        binding.viewModel = viewModel

        ImageHelper.loadImage(viewModel.authUser?.avatar, binding.avatarPicture)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getAllAutarchies()
        }

        val recyclerView: RecyclerView = binding.autarchiesList
        val adapter = AutarchyItemAdapter()
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(CardItemDecoration())

        binding.settingsBtn.setOnClickListener {
            Settings.create(requireActivity())
        }

        viewModel.autarchies.observe(viewLifecycleOwner, Observer { autarchies ->
            adapter.setAutarchies(autarchies)
            binding.totalBurns.text = autarchies.sumOf {
                it.totalBurns
            }.toString()
        })

        return binding.root
    }
}