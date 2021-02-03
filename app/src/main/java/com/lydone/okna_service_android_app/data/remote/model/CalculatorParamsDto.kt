package com.lydone.okna_service_android_app.data.remote.model

import com.google.gson.annotations.SerializedName

data class CalculatorParamsDto(
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("type")
    val materialType: Int,
    @SerializedName("sashes")
    val sashes: List<Int>,
    @SerializedName("glass")
    val glassUnitType: Int,
    @SerializedName("houseType")
    val houseType: Int,
    @SerializedName("windowsill")
    val isWindowsillIncluded: Boolean,
    @SerializedName("tide")
    val isEbbIncluded: Boolean,
    @SerializedName("slopes")
    val isSlopeIncluded: Boolean,
    @SerializedName("lamination")
    val isLaminationIncluded: Boolean,
    @SerializedName("net")
    val isMosquitoNetIncluded: Boolean,
    @SerializedName("mounting")
    val isInstallationIncluded: Boolean,
    @SerializedName("delivery")
    val isDeliveryIncluded: Boolean
)