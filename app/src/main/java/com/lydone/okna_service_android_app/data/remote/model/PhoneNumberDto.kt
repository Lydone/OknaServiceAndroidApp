package com.lydone.okna_service_android_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class PhoneNumberDto(
    @SerializedName("phoneNumber")
    val phoneNumber: String
)