package com.example.firewatch.presentation.adapters.cardItem

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.R
import com.example.firewatch.databinding.CardItemBinding
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.presentation.views.DetailBurnActivity
import com.example.firewatch.shared.helpers.DateHelper
import java.math.BigDecimal
import java.time.LocalDateTime

class CardItemAdapter(val activity: FragmentActivity) : RecyclerView.Adapter<CardItemHolder>() {
    private var burns = emptyList<Burn>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItemHolder {
        val item = CardItemHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        item.setItemClick {
            val intent = Intent(parent.context, DetailBurnActivity::class.java)
            parent.context.startActivity(intent)
        }

        return item
    }

    override fun getItemCount(): Int = burns.size

    override fun onBindViewHolder(holder: CardItemHolder, position: Int) {
        val current = burns[position]
        val cardTitle = holder.binding.cardItemTitle
        val createdAt = holder.binding.cardItemCreatedAt
        val cardStreet = holder.binding.cardItemStreet
        val cardCity = holder.binding.cardItemCity
        val cardZipCode = holder.binding.cardItemZipCode
        val cardCountry = holder.binding.cardItemCountry
        val cardLat = holder.binding.cardItemLat
        val cardLon = holder.binding.cardItemLon

        cardTitle.text = current.title
        setCreatedAt(createdAt, current.beginAt)
        setLat(cardLat, current.coordinates.lat)
        setLon(cardLon, current.coordinates.lon)
    }

    private fun setCreatedAt(createdAt: TextView, date: LocalDateTime) {
        createdAt.text = "${activity.resources.getString(R.string.created_at)} ${DateHelper.getFormattedDate(date)}"
    }
    private fun setLat(latTxt: TextView, coordinate: BigDecimal) {
        latTxt.text = "Lat: ${coordinate.toPlainString()}"
    }
    private fun setLon(lonTxt: TextView, coordinate: BigDecimal) {
        lonTxt.text = "Lon: ${coordinate.toPlainString()}"
    }
    fun setBurns(burns: List<Burn>) {
        this.burns = burns
        notifyDataSetChanged()
    }
}