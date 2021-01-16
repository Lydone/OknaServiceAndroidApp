package com.lydone.okna_service_android_app.presentation.calculator.window_images_view_pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lydone.okna_service_android_app.R

class WindowImagesAdapter : RecyclerView.Adapter<WindowImagesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WindowImagesViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_window_image, parent, false)
    )

    override fun onBindViewHolder(holder: WindowImagesViewHolder, position: Int) = holder.bind(
        requireNotNull(
            ContextCompat.getDrawable(
                holder.itemView.context,
                when (position) {
                    0 -> R.drawable.window_1_sash
                    1 -> R.drawable.window_2_sashes
                    2 -> R.drawable.window_3_sashes
                    else -> throw IllegalStateException("Incorrect position of holder")
                }
            )
        )
    )

    override fun getItemCount() = 3
}