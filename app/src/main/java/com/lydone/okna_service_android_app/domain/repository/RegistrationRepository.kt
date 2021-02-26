package com.lydone.okna_service_android_app.domain.repository


interface RegistrationRepository {

    suspend fun signUp(phoneNumber: String, firebaseToken: String, smsCode: String, name: String, email: String)

}