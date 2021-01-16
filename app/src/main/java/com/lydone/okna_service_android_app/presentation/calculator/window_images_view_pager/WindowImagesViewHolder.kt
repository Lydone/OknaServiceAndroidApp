package com.lydone.okna_service_android_app.presentation.calculator.window_images_view_pager

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class WindowImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(drawable: Drawable) = (itemView as ImageView).setImageDrawable(drawable)
}