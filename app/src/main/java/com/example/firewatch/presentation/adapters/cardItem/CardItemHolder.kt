package com.example.firewatch.presentation.adapters.cardItem

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.CardItemBinding

class CardItemHolder(val binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setItemClick(event: View.OnClickListener?) {
       event?.let {
           itemView.setOnClickListener(event)
       }
    }
}