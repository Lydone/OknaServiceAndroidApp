package com.lydone.okna_service_android_app.domain.model

data class CreateOrderParams(
    val latitude: Double?,
    val longitude: Double?,
    val address: String,
    val windows: List<Window>,
    val houseType: HouseType,
    val isDeliveryIncluded: Boolean,
    val isInstallationIncluded: Boolean,
)
