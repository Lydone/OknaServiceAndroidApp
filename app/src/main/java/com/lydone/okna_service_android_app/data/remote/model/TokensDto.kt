package com.lydone.okna_service_android_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class TokensDto(
    @SerializedName("jwt")
    val accessToken: String?,
    @SerializedName("refreshToken")
    val refreshToken: String?
)