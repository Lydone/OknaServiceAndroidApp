package com.lydone.okna_service_android_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("firebaseId")
    val firebaseToken: String,
    @SerializedName("oneTimeCode")
    val smsCode: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String
)
