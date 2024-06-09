package com.example.firewatch.presentation.adapters.autarchyItem

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.firewatch.databinding.AutarchyItemBinding

class AutarchyItemHolder(val binding: AutarchyItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setItemClick(event: View.OnClickListener?) {
        event?.let {
            itemView.setOnClickListener(event)
        }
    }
}