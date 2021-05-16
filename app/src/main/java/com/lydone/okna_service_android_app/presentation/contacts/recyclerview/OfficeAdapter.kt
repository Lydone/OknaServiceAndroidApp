package com.lydone.okna_service_android_app.presentation.contacts.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lydone.okna_service_android_app.R

class OfficeAdapter : RecyclerView.Adapter<OfficeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OfficeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_holder_office, parent, false)
    )

    override fun onBindViewHolder(holder: OfficeViewHolder, position: Int) {
    }

    override fun getItemCount() = 1
}