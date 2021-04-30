package com.lydone.okna_service_android_app.domain.repository

import com.lydone.okna_service_android_app.domain.model.CreateOrderParams
import com.lydone.okna_service_android_app.domain.model.Order

interface OrderRepository {

    suspend fun createOrder(params: CreateOrderParams): Order
}