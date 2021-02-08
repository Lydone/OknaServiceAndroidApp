package com.lydone.okna_service_android_app.presentation.cart.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.model.Window

class WindowAdapter(
    private val onChangeButtonClicked: (Window) -> Unit,
    private val onDeleteButtonClicked: (Window) -> Unit
) : RecyclerView.Adapter<WindowViewHolder>() {

    private val differ = AsyncListDiffer(this, WindowItemCallback)

    var windows: List<Window>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WindowViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.view_holder_window, parent, false)
    )

    override fun onBindViewHolder(holder: WindowViewHolder, position: Int) =
        holder.bind(windows[position], onChangeButtonClicked, onDeleteButtonClicked)

    override fun getItemCount() = windows.size
}