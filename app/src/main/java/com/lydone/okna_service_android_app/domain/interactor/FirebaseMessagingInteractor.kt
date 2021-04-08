package com.lydone.okna_service_android_app.domain.interactor

import com.lydone.okna_service_android_app.domain.repository.TokenRepository
import javax.inject.Inject

class FirebaseMessagingInteractor @Inject constructor(
    private val tokenRepository: TokenRepository
) {
    fun clearTokens() = tokenRepository.clearTokens()
}