package com.lydone.okna_service_android_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class PaymentUrlRequest(
    @SerializedName("orderId")
    val orderId: Int,
    @SerializedName("isPrepaid")
    val isPrepaid: Boolean,
)
