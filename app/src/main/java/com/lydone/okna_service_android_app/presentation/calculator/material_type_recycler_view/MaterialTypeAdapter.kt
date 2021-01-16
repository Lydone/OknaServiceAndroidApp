package com.lydone.okna_service_android_app.presentation.calculator.material_type_recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.calculator.data.MaterialType

class MaterialTypeAdapter(private val onClick: (MaterialType) -> Unit) :
    RecyclerView.Adapter<MaterialTypeViewHolder>() {

    private val materialTypes = listOf(MaterialType.BUDGET, MaterialType.OPTIMUM, MaterialType.PREMIUM)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MaterialTypeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_holder_material_type, parent, false),
        onClick
    )

    override fun onBindViewHolder(holder: MaterialTypeViewHolder, position: Int) = holder.bind(materialTypes[position])

    override fun getItemCount() = materialTypes.count()
}