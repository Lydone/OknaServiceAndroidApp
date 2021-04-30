package com.lydone.okna_service_android_app.presentation.profile.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lydone.okna_service_android_app.R
import com.lydone.okna_service_android_app.databinding.ViewHolderOrderBinding
import com.lydone.okna_service_android_app.domain.model.Order
import com.lydone.okna_service_android_app.presentation.converter.StatusToStringResConverter

class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = ViewHolderOrderBinding.bind(itemView)

    private val context get() = itemView.context

    fun bind(order: Order, onClick: (Order) -> Unit) {
        with(binding) {
            numberTextView.text = context.getString(R.string.order_number_placeholder, order.id)
//            descriptionTextView.text = order.description
            statusTextView.setText(StatusToStringResConverter.convert(order.status))
            priceTextView.text = context.getString(R.string.ruble_placeholder, order.price)
            root.setOnClickListener { onClick.invoke(order) }
        }
    }
}