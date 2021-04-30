package com.lydone.okna_service_android_app.domain.model

data class Order(
    val id: Int,
    val description: String,
    val price: Int,
    val windows: List<Window>,
    val address: String,
    val status: String,
    val latitude: Double?,
    val longitude: Double?,
)
