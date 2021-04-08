package com.lydone.okna_service_android_app.domain.interactor

import com.lydone.okna_service_android_app.domain.repository.FirebaseMessagingRepository
import com.lydone.okna_service_android_app.domain.repository.RegistrationRepository
import javax.inject.Inject

class RegistrationInteractor @Inject constructor(
    private val registrationRepository: RegistrationRepository,
    private val firebaseMessagingRepository: FirebaseMessagingRepository,
) {

    suspend fun signUp(phoneNumber: String, smsCode: String, name: String, email: String) =
        registrationRepository.signUp(
            phoneNumber = phoneNumber,
            smsCode = smsCode,
            firebaseToken = firebaseMessagingRepository.getToken(),
            name = name,
            email = email,
        )

}