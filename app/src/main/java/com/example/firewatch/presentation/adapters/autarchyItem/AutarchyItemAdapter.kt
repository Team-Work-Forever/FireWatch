package com.example.firewatch.presentation.adapters.autarchyItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.AutarchyItemBinding
import com.example.firewatch.domain.entities.Autarchy
import com.example.firewatch.shared.helpers.ImageHelper

class AutarchyItemAdapter() : RecyclerView.Adapter<AutarchyItemHolder>() {
    private var autarchies = emptyList<Autarchy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutarchyItemHolder {
        return AutarchyItemHolder(AutarchyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = autarchies.size

    override fun onBindViewHolder(holder: AutarchyItemHolder, position: Int) {
        val current = autarchies[position]

        holder.binding.autarchyEmail.text = current.email
        holder.binding.autarchyCityName.text = current.address.city
        holder.binding.autarchyActiveBurnNumber.text = "400"
        ImageHelper.loadImage(current.avatar, holder.binding.autarchyAvatar)

        holder.setItemClick {}
    }

    fun setAutarchies(autarchies: List<Autarchy>) {
        this.autarchies = autarchies
        notifyDataSetChanged()
    }
}