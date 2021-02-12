package com.lydone.okna_service_android_app.domain.interactor

import com.lydone.okna_service_android_app.domain.repository.LoginRepository
import javax.inject.Inject

class LoginInteractor @Inject constructor(private val loginRepository: LoginRepository) {

    suspend fun sendSmsCode(phoneNumber: String) = loginRepository.sendSmsCode(phoneNumber)
}