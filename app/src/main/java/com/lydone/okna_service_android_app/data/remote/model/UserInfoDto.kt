package com.lydone.okna_service_android_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserInfoDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
)