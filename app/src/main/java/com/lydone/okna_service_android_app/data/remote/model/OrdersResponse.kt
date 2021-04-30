package com.lydone.okna_service_android_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class OrdersResponse(
    @SerializedName("orders")
    val orders: List<OrderResponse>,
)
