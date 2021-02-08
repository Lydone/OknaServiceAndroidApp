package com.lydone.okna_service_android_app.presentation.core

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PaddingItemDecoration(
    private val paddingStartEnd: Int,
    private val paddingTopBottom: Int,
    private val paddingMiddle: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutPosition = parent.getChildViewHolder(view).layoutPosition
        if (layoutPosition == RecyclerView.NO_POSITION) return
        val paddingTop = if (layoutPosition == 0) paddingTopBottom else paddingMiddle / 2
        val paddingBottom = if (layoutPosition == state.itemCount - 1) paddingTopBottom else paddingMiddle / 2
        outRect.set(paddingStartEnd, paddingTop, paddingStartEnd, paddingBottom)
    }

}