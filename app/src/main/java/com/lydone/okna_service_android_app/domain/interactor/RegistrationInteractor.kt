package com.lydone.okna_service_android_app.domain.interactor

import com.lydone.okna_service_android_app.domain.repository.RegistrationRepository
import javax.inject.Inject

class RegistrationInteractor @Inject constructor(
    private val registrationRepository: RegistrationRepository
) {

    suspend fun signUp(phoneNumber: String, smsCode: String, name: String, email: String) =
        registrationRepository.signUp(
            phoneNumber = phoneNumber,
            smsCode = smsCode,
            firebaseToken = "NO_TOKEN",
            name = name,
            email = email
        )

}