package com.lydone.okna_service_android_app.data.repository

import com.lydone.okna_service_android_app.data.remote.ApiMapper
import com.lydone.okna_service_android_app.data.remote.model.PhoneNumberDto
import com.lydone.okna_service_android_app.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val apiMapper: ApiMapper) : LoginRepository {

    override suspend fun sendSmsCode(phoneNumber: String) =
        apiMapper.sendSmsCode(PhoneNumberDto("7$phoneNumber"))
}