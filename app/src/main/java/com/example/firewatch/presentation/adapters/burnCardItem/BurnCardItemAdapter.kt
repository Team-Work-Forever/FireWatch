package com.example.firewatch.presentation.adapters.burnCardItem

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.R
import com.example.firewatch.databinding.BurnCardItemBinding
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.presentation.adapters.cardItem.CardItemHolder
import com.example.firewatch.shared.helpers.DateHelper
import com.example.firewatch.shared.helpers.ImageHelper
import java.math.BigDecimal

class BurnCardItemAdapter(

) : RecyclerView.Adapter<BurnCardItemHolder>() {
    private var burns = emptyList<Burn>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BurnCardItemHolder {
        return BurnCardItemHolder(BurnCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = burns.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BurnCardItemHolder, position: Int) {
        val current = burns[position]

        val cardStreet = holder.binding.cardItemStreet
        val cardCity = holder.binding.cardItemCity
        val cardZipCode = holder.binding.cardItemZipCode
        val cardCountry = holder.binding.cardItemCountry
        val cardLat = holder.binding.cardItemLat
        val cardLon = holder.binding.cardItemLon
        val email = holder.binding.cardEmail
        val username = holder.binding.cardUsername
        val nif = holder.binding.cardNif
        val photo = holder.binding.profileImg
        val phone = holder.binding.cardPhone
        val initDate = holder.binding.initDate

        email.text = current.publicProfile?.email
        username.text = current.publicProfile?.userName
        nif.text = "${holder.itemView.resources.getString(R.string.vat_title)} ${current.publicProfile?.nif}"
        phone.text = "${holder.itemView.resources.getString(R.string.phone)} ${current.publicProfile?.phone?.number}"
        ImageHelper.loadImage(current.publicProfile?.avatar, photo)

        cardStreet.text = current.address?.street
        cardCity.text = current.address?.city
        cardZipCode.text = current.address?.zipCode
        cardCountry.text = "Portugal"
        setLat(cardLat, current.coordinates.lat)
        setLon(cardLon, current.coordinates.lon)
        initDate.text = "${holder.itemView.resources.getString(R.string.iniit_at)} ${DateHelper.getFormattedDate(current.beginAt)}"
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