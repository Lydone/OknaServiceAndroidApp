package com.lydone.okna_service_android_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class WindowDimensionsLimitsResponse(
    @SerializedName("minW")
    val minWidth: Int?,
    @SerializedName("minH")
    val minHeight: Int?,
    @SerializedName("maxW")
    val maxWidth: Int?,
    @SerializedName("maxH")
    val maxHeight: Int?
)
