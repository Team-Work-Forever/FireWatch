package com.example.firewatch.presentation.adapters.burnCardItem

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.BurnCardItemBinding
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.presentation.adapters.cardItem.CardItemHolder
import java.math.BigDecimal

class BurnCardItemAdapter(

) : RecyclerView.Adapter<BurnCardItemHolder>() {
    private var burns = emptyList<Burn>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BurnCardItemHolder {
        return BurnCardItemHolder(BurnCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = burns.size

    override fun onBindViewHolder(holder: BurnCardItemHolder, position: Int) {
        val current = burns[position]

        val cardStreet = holder.binding.cardItemStreet
        val cardCity = holder.binding.cardItemCity
        val cardZipCode = holder.binding.cardItemZipCode
        val cardCountry = holder.binding.cardItemCountry
        val cardLat = holder.binding.cardItemLat
        val cardLon = holder.binding.cardItemLon

        cardStreet.text = current.address?.street
        cardCity.text = current.address?.city
        cardZipCode.text = current.address?.zipCode
        cardCountry.text = "Portugal"
        setLat(cardLat, current.coordinates.lat)
        setLon(cardLon, current.coordinates.lon)

    }

    @SuppressLint("SetTextI18n")
    private fun setLat(latTxt: TextView, coordinate: BigDecimal) {
        latTxt.text = "Lat: ${coordinate.toPlainString()}"
    }

    @SuppressLint("SetTextI18n")
    private fun setLon(lonTxt: TextView, coordinate: BigDecimal) {
        lonTxt.text = "Lon: ${coordinate.toPlainString()}"
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setBurns(burns: List<Burn>) {
        this.burns = burns
        notifyDataSetChanged()
    }
}