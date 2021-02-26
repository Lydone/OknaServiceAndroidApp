package com.lydone.okna_service_android_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("userName")
    val phoneNumber: String,
    @SerializedName("firebaseId")
    val firebaseToken: String,
    @SerializedName("oneTimeCode")
    val smsCode: String
)