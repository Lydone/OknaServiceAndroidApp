package com.lydone.okna_service_android_app.domain.repository

interface FirebaseMessagingRepository {

    suspend fun getToken(): String
}