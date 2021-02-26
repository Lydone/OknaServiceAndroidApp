package com.lydone.okna_service_android_app.data.repository

import com.lydone.okna_service_android_app.data.storage.TokenSharedPreferencesStorage
import com.lydone.okna_service_android_app.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val tokenStorage: TokenSharedPreferencesStorage
) : OrderRepository {
    override suspend fun createOrder() {
        //TODO переделать нормально под сервер
        throw IllegalArgumentException("No token")
        if (tokenStorage.accessToken == null) {
            throw IllegalArgumentException("No token")
        }
    }
}