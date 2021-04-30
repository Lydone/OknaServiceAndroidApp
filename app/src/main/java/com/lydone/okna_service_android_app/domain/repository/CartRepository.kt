package com.lydone.okna_service_android_app.domain.repository

import com.lydone.okna_service_android_app.domain.model.Window
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    fun getWindows(): Flow<List<Window>>

    suspend fun addWindow(window: Window)

    suspend fun deleteWindow(window: Window)

    suspend fun updateWindow(window: Window)

    suspend fun getWindowById(id: Int): Window

    suspend fun deleteWindows()
}