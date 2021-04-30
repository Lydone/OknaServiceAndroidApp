package com.lydone.okna_service_android_app.domain.interactor

import com.lydone.okna_service_android_app.domain.repository.AuthRepository
import com.lydone.okna_service_android_app.domain.repository.FirebaseMessagingRepository
import javax.inject.Inject

class RegistrationInteractor @Inject constructor(
    private val authRepository: AuthRepository,
    private val firebaseMessagingRepository: FirebaseMessagingRepository,
) {

    suspend fun signUp(phoneNumber: String, smsCode: String, name: String, email: String) =
        authRepository.signUp(
            phoneNumber = phoneNumber,
            smsCode = smsCode,
            firebaseToken = firebaseMessagingRepository.getToken(),
            name = name,
            email = email,
        )

}