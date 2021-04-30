package com.lydone.okna_service_android_app.domain.interactor

import com.lydone.okna_service_android_app.domain.repository.AuthRepository
import javax.inject.Inject

class SmsCodeInteractor @Inject constructor(private val authRepository: AuthRepository) {

    suspend fun sendSmsCode(phoneNumber: String) = authRepository.sendSmsCode(phoneNumber)
}