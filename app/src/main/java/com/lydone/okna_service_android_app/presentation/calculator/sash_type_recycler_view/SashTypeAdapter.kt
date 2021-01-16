package com.lydone.okna_service_android_app.presentation.calculator.sash_type_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.presentation.calculator.model.SashType

class SashTypeAdapter(
    private val onSashTypeChanged: (position: Int, newType: SashType) -> Unit
) : RecyclerView.Adapter<SashTypeViewHolder>() {

    var sashTypes = listOf<SashType>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SashTypeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_holder_sash_type, parent, false),
        onSashTypeChanged
    )

    override fun onBindViewHolder(holder: SashTypeViewHolder, position: Int) =
        holder.bind(position, sashTypes[position])

    override fun getItemCount() = sashTypes.size
}