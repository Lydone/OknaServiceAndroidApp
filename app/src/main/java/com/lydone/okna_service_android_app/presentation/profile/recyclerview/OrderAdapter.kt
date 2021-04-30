package com.lydone.okna_service_android_app.presentation.profile.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.domain.model.Order

class OrderAdapter(private val onClick: (Order) -> Unit) : RecyclerView.Adapter<OrderViewHolder>() {

    var orders: List<Order> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OrderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_order, parent, false))

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) = holder.bind(orders[position], onClick)

    override fun getItemCount() = orders.size
}