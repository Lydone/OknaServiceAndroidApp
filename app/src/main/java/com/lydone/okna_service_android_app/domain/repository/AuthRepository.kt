package com.lydone.okna_service_android_app.domain.repository

interface AuthRepository {

    fun clearTokens()

    suspend fun refreshToken()

    suspend fun sendSmsCode(phoneNumber: String)

    suspend fun login(phoneNumber: String, firebaseToken: String, smsCode: String)

    suspend fun signUp(phoneNumber: String, firebaseToken: String, smsCode: String, name: String, email: String)
}