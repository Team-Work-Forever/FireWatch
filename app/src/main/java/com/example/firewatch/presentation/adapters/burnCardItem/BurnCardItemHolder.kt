package com.example.firewatch.presentation.adapters.burnCardItem

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.BurnCardItemBinding

class BurnCardItemHolder(val binding: BurnCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setItemClick(event: View.OnClickListener?) {
        event?.let {
            itemView.setOnClickListener(event)
        }
    }
}