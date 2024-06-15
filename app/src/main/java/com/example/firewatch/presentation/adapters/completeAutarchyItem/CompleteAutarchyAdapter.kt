package com.example.firewatch.presentation.adapters.completeAutarchyItem

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.CompleteAutarchyItemBinding
import com.example.firewatch.domain.entities.Autarchy
import com.example.firewatch.presentation.views.DetailAutarchy
import com.example.firewatch.shared.helpers.ImageHelper

class CompleteAutarchyAdapter : RecyclerView.Adapter<CompleteAutarchyHolder>() {
    private var autarchies = emptyList<Autarchy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompleteAutarchyHolder {
        return CompleteAutarchyHolder(CompleteAutarchyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = autarchies.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CompleteAutarchyHolder, position: Int) {
        val current = autarchies[position]

        holder.binding.autarchyEmail.text = current.email
        holder.binding.autarchyCityName.text = current.address.city
        holder.binding.autarchyPhone.text = "Tel. ${current.phone.number}"
        holder.binding.cardItemStreet.text = current.address.street
        holder.binding.cardItemCity.text = current.address.city
        holder.binding.cardItemZipCode.text = current.address.zipCode
        holder.binding.cardItemCountry.text = "Portugal"
        holder.binding.cardItemLat.text = "Lat. ${current.coordinates.lat}"
        holder.binding.cardItemLon.text = "Lon. ${current.coordinates.lon}"

        ImageHelper.loadImage(current.avatar, holder.binding.autarchyAvatar)

        holder.setItemClick {
            DetailAutarchy.new(holder.itemView.context, current.id)
        }
    }

    fun setAutarchies(autarchies: List<Autarchy>) {
        this.autarchies = autarchies
        notifyDataSetChanged()
    }
}