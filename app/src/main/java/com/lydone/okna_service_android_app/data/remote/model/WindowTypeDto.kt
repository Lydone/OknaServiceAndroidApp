package com.lydone.okna_service_android_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class WindowTypeDto(
    @SerializedName("sashes")
    val windowType: Int?
)