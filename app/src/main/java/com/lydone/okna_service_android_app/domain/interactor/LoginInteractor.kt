package com.lydone.okna_service_android_app.domain.interactor

import com.lydone.okna_service_android_app.domain.repository.LoginRepository
import javax.inject.Inject

class LoginInteractor @Inject constructor(private val loginRepository: LoginRepository) {

    suspend fun login(phoneNumber: String, smsCode: String) =
        loginRepository.login(phoneNumber = phoneNumber, smsCode = smsCode, firebaseToken = "NO_TOKEN")
}