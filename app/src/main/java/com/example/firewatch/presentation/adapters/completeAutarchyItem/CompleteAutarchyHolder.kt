package com.example.firewatch.presentation.adapters.completeAutarchyItem

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.CompleteAutarchyItemBinding

class CompleteAutarchyHolder(val binding: CompleteAutarchyItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun setItemClick(event: View.OnClickListener?) {
        event?.let {
            itemView.setOnClickListener(event)
        }
    }
}