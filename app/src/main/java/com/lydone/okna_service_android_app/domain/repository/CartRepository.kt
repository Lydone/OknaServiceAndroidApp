package com.lydone.okna_service_android_app.domain.repository

import com.lydone.okna_service_android_app.domain.model.Window

interface CartRepository {

    suspend fun addWindowToCart(window: Window)
}