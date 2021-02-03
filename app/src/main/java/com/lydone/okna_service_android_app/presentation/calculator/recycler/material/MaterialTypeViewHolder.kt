package com.lydone.okna_service_android_app.presentation.calculator.recycler.material

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.model.MaterialType
import com.lydone.okna_service_android_app.presentation.calculator.converter.MaterialTypeToStringResConverter

class MaterialTypeViewHolder(itemView: View, private val onClick: (MaterialType) -> Unit) :
    RecyclerView.ViewHolder(itemView) {

    private val titleTextView: TextView = itemView.findViewById(R.id.title)
    private val descriptionTextView: TextView = itemView.findViewById(R.id.description)

    fun bind(materialType: MaterialType) {
        titleTextView.setText(MaterialTypeToStringResConverter.convertToTitleString(materialType))
        descriptionTextView.setText(MaterialTypeToStringResConverter.convertToDescriptionString(materialType))
        itemView.setOnClickListener { onClick.invoke(materialType) }
    }
}