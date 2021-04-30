package com.lydone.okna_service_android_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class OrderResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("totalCost")
    val price: Int?,
    @SerializedName("orderListItems")
    val windows: List<WindowDto>?,
    @SerializedName("addressDescription")
    val address: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("long")
    val longitude: Double,
)
