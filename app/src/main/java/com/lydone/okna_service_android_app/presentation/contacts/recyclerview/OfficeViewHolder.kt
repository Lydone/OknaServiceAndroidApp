package com.lydone.okna_service_android_app.presentation.contacts.recyclerview

import android.view.View
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.lydone.okna_service_android_app.databinding.ViewHolderOfficeBinding

class OfficeViewHolder(private val binding: ViewHolderOfficeBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(@DrawableRes drawableRes: Int, onCallButtonClick: (View) -> Unit, onOpenOnMapButtonClick: (View) -> Unit) {
        binding.imageView.setImageResource(drawableRes)
        binding.callButton.setOnClickListener(onCallButtonClick)
        binding.openOnMapButton.setOnClickListener(onOpenOnMapButtonClick)
    }

}