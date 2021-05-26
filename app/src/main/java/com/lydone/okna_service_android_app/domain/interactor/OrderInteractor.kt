package com.lydone.okna_service_android_app.domain.interactor

import com.lydone.okna_service_android_app.domain.repository.AuthRepository
import com.lydone.okna_service_android_app.domain.repository.OrderRepository
import javax.inject.Inject

class OrderInteractor @Inject constructor(
    private val orderRepository: OrderRepository,
    private val authRepository: AuthRepository,
) {
    suspend fun getOrder(id: Int) =
        try {
            orderRepository.getOrders().first { it.id == id }
        } catch (e: Exception) {
            authRepository.refreshToken()
            orderRepository.getOrders().first { it.id == id }
        }

    suspend fun getPaymentUrl(orderId: Int, isPrepayment: Boolean) =
        orderRepository.getPaymentUrl(orderId, isPrepayment)
}