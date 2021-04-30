package com.lydone.okna_service_android_app.domain.repository

import com.lydone.okna_service_android_app.domain.model.UserInfo

interface UserRepository {

    suspend fun getUserInfo(): UserInfo
}