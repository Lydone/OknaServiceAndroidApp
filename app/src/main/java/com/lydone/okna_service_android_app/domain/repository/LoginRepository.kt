package com.lydone.okna_service_android_app.domain.repository

interface LoginRepository {
    suspend fun sendSmsCode(phoneNumber: String)
}