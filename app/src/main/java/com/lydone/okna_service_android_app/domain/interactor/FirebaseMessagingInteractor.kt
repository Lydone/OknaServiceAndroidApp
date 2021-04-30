package com.lydone.okna_service_android_app.domain.interactor

import com.lydone.okna_service_android_app.domain.repository.AuthRepository
import javax.inject.Inject

class FirebaseMessagingInteractor @Inject constructor(
    private val authRepository: AuthRepository
) {
    fun clearTokens() = authRepository.clearTokens()
}