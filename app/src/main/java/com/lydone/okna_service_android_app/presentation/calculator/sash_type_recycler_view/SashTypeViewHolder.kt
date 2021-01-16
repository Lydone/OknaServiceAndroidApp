package com.lydone.okna_service_android_app.presentation.calculator.sash_type_recycler_view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.presentation.calculator.converter.ChipIdToSashTypeConverter
import com.lydone.okna_service_android_app.presentation.calculator.model.SashType

class SashTypeViewHolder(
    itemView: View,
    private val onSashTypeChanged: (position: Int, newType: SashType) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val selectSashTypeTextView: TextView = itemView.findViewById(R.id.sash_types_title)
    private val chipGroup = itemView.findViewById(R.id.sash_types) as ChipGroup

    fun bind(sashPosition: Int, checkedSashType: SashType) {
        chipGroup.setOnCheckedChangeListener(null)
        selectSashTypeTextView.text =
            itemView.context.getString(R.string.sash_type_placeholder, sashPosition + 1)
        chipGroup.check(ChipIdToSashTypeConverter.convertBack(checkedSashType))
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            onSashTypeChanged.invoke(
                sashPosition,
                ChipIdToSashTypeConverter.convert(checkedId)
            )
        }
    }
}