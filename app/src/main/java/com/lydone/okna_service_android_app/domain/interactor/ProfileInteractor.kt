package com.lydone.okna_service_android_app.domain.interactor

import com.lydone.okna_service_android_app.domain.repository.AuthRepository
import com.lydone.okna_service_android_app.domain.repository.OrderRepository
import com.lydone.okna_service_android_app.domain.repository.UserRepository
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val orderRepository: OrderRepository,
) {

    suspend fun getUserInfo() =
        try {
            userRepository.getUserInfo()
        } catch (e: Exception) {
            authRepository.refreshToken()
            userRepository.getUserInfo()
        }

    suspend fun getOrders() =
        try {
            orderRepository.getOrders()
        } catch (e: Exception) {
            authRepository.refreshToken()
            orderRepository.getOrders()
        }

    fun logout() = authRepository.clearTokens()

}