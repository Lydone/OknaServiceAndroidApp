package com.lydone.okna_service_android_app.data.repository

import com.lydone.okna_service_android_app.data.remote.ApiMapper
import com.lydone.okna_service_android_app.domain.model.UserInfo
import com.lydone.okna_service_android_app.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val apiMapper: ApiMapper) : UserRepository {

    override suspend fun getUserInfo() = apiMapper.getUserInfo().let { response ->
        UserInfo(
            name = response.name,
            email = response.email,
            phoneNumber = response.phoneNumber,
        )
    }
}