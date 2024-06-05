package com.example.firewatch.presentation.adapters.cardItem

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.CardItemBinding

class CardItemAdapter : RecyclerView.Adapter<CardItemHolder>() {
    private val items: List<String> = listOf(
        "Long Live to The Rebels Comunity",
        "Betrayal of The Republic",
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardItemHolder {
        return CardItemHolder(CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CardItemHolder, position: Int) {
        val current = items[position]
        val cardTitle = holder.binding.cardItemTitle

        cardTitle.text = current
    }
}