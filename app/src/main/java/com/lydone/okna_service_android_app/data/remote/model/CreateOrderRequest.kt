package com.lydone.okna_service_android_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class CreateOrderRequest(
    @SerializedName("lat")
    val latitude: Double?,
    @SerializedName("long")
    val longitude: Double?,
    @SerializedName("addressDescription")
    val address: String,
    @SerializedName("listItems")
    val windows: List<WindowDto>
)
