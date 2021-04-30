package com.lydone.okna_service_android_app.data.repository

import com.google.gson.Gson
import com.lydone.okna_service_android_app.data.remote.ApiMapper
import com.lydone.okna_service_android_app.data.remote.model.*
import com.lydone.okna_service_android_app.data.storage.TokenSharedPreferencesStorage
import com.lydone.okna_service_android_app.domain.exception.IncorrectOtpException
import com.lydone.okna_service_android_app.domain.exception.UserDoesNotExistException
import com.lydone.okna_service_android_app.domain.repository.AuthRepository
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiMapper: ApiMapper,
    tokenStorage: TokenSharedPreferencesStorage
) : AuthRepository {

    private var accessToken by tokenStorage::accessToken

    private var refreshToken by tokenStorage::refreshToken

    override fun clearTokens() {
        refreshToken = null
        accessToken = null
    }

    override suspend fun refreshToken() {
        apiMapper.refreshToken(RefreshTokenRequest(refreshToken ?: "")).saveTokens()
    }

    override suspend fun sendSmsCode(phoneNumber: String) =
        apiMapper.sendSmsCode(PhoneNumberDto("7$phoneNumber"))

    override suspend fun login(phoneNumber: String, firebaseToken: String, smsCode: String) {
        try {
            apiMapper.login(
                LoginRequest(phoneNumber = "7$phoneNumber", firebaseToken = firebaseToken, smsCode = smsCode)
            ).saveTokens()
        } catch (e: HttpException) {
            throw when (Gson().fromJson(
                requireNotNull(e.response()?.errorBody()?.charStream()),
                ErrorResponse::class.java
            ).type) {
                ErrorResponse.Type.INCORRECT_OTP -> IncorrectOtpException()
                ErrorResponse.Type.USER_DOES_NOT_EXIST -> UserDoesNotExistException()
            }
        }
    }


    override suspend fun signUp(
        phoneNumber: String,
        firebaseToken: String,
        smsCode: String,
        name: String,
        email: String
    ) {
        apiMapper.signUp(
            SignUpRequest(
                phoneNumber = "7$phoneNumber",
                firebaseToken = firebaseToken,
                smsCode = smsCode,
                name = name,
                email = email,
            )
        ).saveTokens()
    }

    private fun TokensDto.saveTokens() {
        this@AuthRepositoryImpl.accessToken = accessToken
        this@AuthRepositoryImpl.refreshToken = refreshToken
    }
}