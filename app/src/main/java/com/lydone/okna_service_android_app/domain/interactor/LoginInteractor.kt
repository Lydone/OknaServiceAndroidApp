package com.lydone.okna_service_android_app.domain.interactor

import com.lydone.okna_service_android_app.domain.repository.AuthRepository
import com.lydone.okna_service_android_app.domain.repository.FirebaseMessagingRepository
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    private val authRepository: AuthRepository,
    private val firebaseMessagingRepository: FirebaseMessagingRepository,
) {

    suspend fun login(phoneNumber: String, smsCode: String) =
        authRepository.login(
            phoneNumber = phoneNumber,
            smsCode = smsCode,
            firebaseToken = firebaseMessagingRepository.getToken(),
        )
}