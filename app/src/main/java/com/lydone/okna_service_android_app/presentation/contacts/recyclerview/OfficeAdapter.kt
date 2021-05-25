package com.lydone.okna_service_android_app.presentation.contacts.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lydone.okna_service_android_app.databinding.ViewHolderOfficeBinding

class OfficeAdapter(
    private val onCallButtonClick: (phoneNumber: String) -> Unit,
    private val onOpenOnMapButtonClick: (lat: Double, long: Double) -> Unit
) : RecyclerView.Adapter<OfficeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = OfficeViewHolder(
        ViewHolderOfficeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: OfficeViewHolder, position: Int) = holder.bind(
        onCallButtonClick = { onCallButtonClick.invoke("+74951197619") },
        onOpenOnMapButtonClick = { onOpenOnMapButtonClick.invoke(55.41925000747525, 37.55421813494572) }
    )

    override fun getItemCount() = 1
}