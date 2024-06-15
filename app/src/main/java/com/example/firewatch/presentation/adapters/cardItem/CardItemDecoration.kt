package com.example.firewatch.presentation.adapters.cardItem

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CardItemDecoration : RecyclerView.ItemDecoration() {
    companion object {
        private const val SPACE_CARD_ITEM = 30
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = SPACE_CARD_ITEM
    }
}