package com.lydone.okna_service_android_app.data.repository

import com.lydone.okna_service_android_app.data.storage.TokenSharedPreferencesStorage
import com.lydone.okna_service_android_app.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenStorage: TokenSharedPreferencesStorage
) : TokenRepository {

    override fun clearTokens() {
        tokenStorage.refreshToken = null
        tokenStorage.accessToken = null
    }

}