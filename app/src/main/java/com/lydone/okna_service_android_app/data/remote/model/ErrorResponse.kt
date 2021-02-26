package com.lydone.okna_service_android_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("errorCode")
    val type: Type
) {

    enum class Type {
        @SerializedName("InvalidUsernameOrOTP")
        INCORRECT_OTP,

        @SerializedName("NoUserExists")
        USER_DOES_NOT_EXIST
    }
}