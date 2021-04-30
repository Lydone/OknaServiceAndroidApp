package com.lydone.okna_service_android_app.data.remote.converter

import com.lydone.okna_service_android_app.data.remote.model.OrderResponse
import com.lydone.okna_service_android_app.domain.model.Order

object OrderConverter {

    fun toModel(response: OrderResponse) = with(response) {
        Order(
            id = requireNotNull(id),
            description = requireNotNull(description),
            price = requireNotNull(price),
            windows = requireNotNull(windows).map { WindowDtoConverter.toModel(it) },
            address = requireNotNull(address),
            status = StatusConverter.toModel(requireNotNull(status)),
            latitude = latitude,
            longitude = longitude
        )
    }

    private object StatusConverter {

        fun toModel(status: OrderResponse.Status) = when (status) {
            OrderResponse.Status.CREATED -> Order.Status.CREATED
            OrderResponse.Status.PREPAID -> Order.Status.PREPAID
            OrderResponse.Status.IN_WORK -> Order.Status.IN_WORK
            OrderResponse.Status.DONE -> Order.Status.DONE
        }
    }

}