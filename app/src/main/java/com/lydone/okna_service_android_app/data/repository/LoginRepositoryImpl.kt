package com.lydone.okna_service_android_app.data.repository

import com.google.gson.Gson
import com.lydone.okna_service_android_app.data.remote.ApiMapper
import com.lydone.okna_service_android_app.data.remote.model.ErrorResponse
import com.lydone.okna_service_android_app.data.remote.model.LoginRequest
import com.lydone.okna_service_android_app.data.remote.model.PhoneNumberDto
import com.lydone.okna_service_android_app.data.storage.TokenSharedPreferencesStorage
import com.lydone.okna_service_android_app.domain.exception.IncorrectOtpException
import com.lydone.okna_service_android_app.domain.exception.UserDoesNotExistException
import com.lydone.okna_service_android_app.domain.repository.LoginRepository
import retrofit2.HttpException
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiMapper: ApiMapper,
    private val tokenStorage: TokenSharedPreferencesStorage
) : LoginRepository {

    override suspend fun sendSmsCode(phoneNumber: String) =
        apiMapper.sendSmsCode(PhoneNumberDto("7$phoneNumber"))

    override suspend fun login(phoneNumber: String, firebaseToken: String, smsCode: String) = try {
        with(
            apiMapper.login(
                LoginRequest(phoneNumber = "7$phoneNumber", firebaseToken = firebaseToken, smsCode = smsCode)
            )
        ) {
            tokenStorage.accessToken = accessToken
            tokenStorage.refreshToken = refreshToken
        }
    } catch (e: HttpException) {
        throw when (Gson().fromJson(requireNotNull(e.response()?.errorBody()?.charStream()), ErrorResponse::class.java).type) {
            ErrorResponse.Type.INCORRECT_OTP -> IncorrectOtpException()
            ErrorResponse.Type.USER_DOES_NOT_EXIST -> UserDoesNotExistException()
        }
    }

}