package com.lydone.okna_service_android_app.presentation.calculator.recycler.sash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.model.SashType

class SashTypeAdapter(
    private val onSashTypeChanged: (position: Int, newType: SashType) -> Unit
) : RecyclerView.Adapter<SashTypeViewHolder>() {

    var checkedSashes = listOf<SashType>()
        set(value) {
            if (field.size > value.size) {
                notifyItemRangeRemoved(value.size, field.size - value.size)
                notifyItemRangeChanged(0, value.size)
            }
            if (field.size < value.size) {
                notifyItemRangeInserted(field.size, value.size - field.size)
                notifyItemRangeChanged(0, value.size)
            }
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SashTypeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_holder_sash_type, parent, false),
        onSashTypeChanged
    )

    override fun onBindViewHolder(holder: SashTypeViewHolder, position: Int) =
        holder.bind(position, checkedSashes[position])

    override fun getItemCount() = checkedSashes.size
}