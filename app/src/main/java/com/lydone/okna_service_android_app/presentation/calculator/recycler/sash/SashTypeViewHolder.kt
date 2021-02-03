package com.lydone.okna_service_android_app.presentation.calculator.recycler.sash

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.model.SashType
import com.lydone.okna_service_android_app.presentation.calculator.converter.ChipIdToSashTypeConverter

class SashTypeViewHolder(
    itemView: View,
    private val onSashTypeChanged: (position: Int, newType: SashType) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val selectSashTypeTextView: TextView = itemView.findViewById(R.id.sash_types_title)
    private val chipGroup = itemView.findViewById(R.id.sash_types) as ChipGroup

    fun bind(sashPosition: Int, checkedSashType: SashType) {
        selectSashTypeTextView.text =
            itemView.context.getString(R.string.sash_type_placeholder, sashPosition + 1)
        chipGroup.setOnCheckedChangeListener(null)
        chipGroup.check(ChipIdToSashTypeConverter.convertBack(checkedSashType))
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            onSashTypeChanged.invoke(sashPosition, ChipIdToSashTypeConverter.convert(checkedId))
        }
    }
}