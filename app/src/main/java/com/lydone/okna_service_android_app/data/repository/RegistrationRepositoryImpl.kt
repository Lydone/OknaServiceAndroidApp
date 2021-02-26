package com.lydone.okna_service_android_app.data.repository

import com.lydone.okna_service_android_app.data.remote.ApiMapper
import com.lydone.okna_service_android_app.data.remote.model.SignUpRequest
import com.lydone.okna_service_android_app.data.storage.TokenSharedPreferencesStorage
import com.lydone.okna_service_android_app.domain.repository.RegistrationRepository
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val apiMapper: ApiMapper,
    private val tokenStorage: TokenSharedPreferencesStorage
) : RegistrationRepository {

    override suspend fun signUp(
        phoneNumber: String,
        firebaseToken: String,
        smsCode: String,
        name: String,
        email: String
    ) {
        with(
            apiMapper.signUp(
                SignUpRequest(
                    phoneNumber = "7$phoneNumber",
                    firebaseToken = firebaseToken,
                    smsCode = smsCode,
                    name = name,
                    email = email
                )
            )
        ) {
            tokenStorage.accessToken = accessToken
            tokenStorage.refreshToken = refreshToken
        }
    }
}