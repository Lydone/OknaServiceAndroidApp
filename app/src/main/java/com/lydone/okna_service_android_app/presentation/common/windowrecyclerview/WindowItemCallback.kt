package com.lydone.okna_service_android_app.presentation.common.windowrecyclerview

import androidx.recyclerview.widget.DiffUtil
import com.lydone.okna_service_android_app.domain.model.Window

object WindowItemCallback : DiffUtil.ItemCallback<Window>() {
    override fun areItemsTheSame(oldItem: Window, newItem: Window) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Window, newItem: Window) = oldItem == newItem
}