package com.example.firewatch.presentation.adapters.cardItem

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.R
import com.example.firewatch.databinding.CardItemBinding
import com.example.firewatch.domain.entities.Burn
import com.example.firewatch.domain.repositories.interfaces.BurnRepository
import com.example.firewatch.domain.valueObjects.BurnState
import com.example.firewatch.presentation.views.DetailBurnActivity
import com.example.firewatch.shared.helpers.DateHelper
import java.math.BigDecimal
import java.time.LocalDateTime

typealias OnBottomClick = (detail: Burn) -> Unit

class CardItemAdapter(
    val activity: FragmentActivity,
    private val bottomClick: OnBottomClick? = null,
    private val hasBottom: Boolean = false,
) : RecyclerView.Adapter<CardItemHolder>() {
    private var burns = emptyList<Burn>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItemHolder {
        return CardItemHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
        val badge = holder.binding.burnStateBadge

        cardStreet.text = current.address?.street
        cardCity.text = current.address?.city
        cardZipCode.text = current.address?.zipCode
        cardCountry.text = "Portugal"

        cardTitle.text = current.title
        setCreatedAt(createdAt, current.beginAt)
        setLat(cardLat, current.coordinates.lat)
        setLon(cardLon, current.coordinates.lon)

        val resources = holder.itemView.resources
        val actionBtn = holder.binding.actionBtn
        actionBtn.setOnClickListener {
            bottomClick?.invoke(current)
        }

        when (current.state) {
            BurnState.ACTIVE -> {
                actionBtn.text = resources.getString(R.string.finish)
                actionBtn.setBackgroundColor(resources.getColor(R.color.orange))

                setBadge(badge, resources, R.string.active, R.color.orange)
            }
            BurnState.SCHEDULED -> {
                actionBtn.text = resources.getString(R.string.start)
                actionBtn.setBackgroundColor(resources.getColor(R.color.teal_700))

                setBadge(badge, resources, R.string.schedualed, R.color.blueish)
            }
            BurnState.REJECTED -> {
                setBadge(badge, resources, R.string.rejected, R.color.redish)
            }
            BurnState.COMPLETED -> {
                actionBtn.text = resources.getString(R.string.repeat)
                actionBtn.setBackgroundColor(resources.getColor(R.color.middle_gray))

                setBadge(badge, resources, R.string.completed, R.color.yelloish)
            }
        }

        holder.setItemClick {
            DetailBurnActivity.new(holder.itemView.context, current.id)
        }
    }

    fun setBadge(button: AppCompatButton, resources: Resources, text: Int, color: Int) {
        button.setBackgroundTintList(ColorStateList.valueOf(resources.getColor(color)))
        button.text = resources.getString(text)
    }

    @SuppressLint("SetTextI18n")
    private fun setCreatedAt(createdAt: TextView, date: LocalDateTime) {
        createdAt.text = "${activity.resources.getString(R.string.created_at)} ${DateHelper.getFormattedDate(date)}"
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