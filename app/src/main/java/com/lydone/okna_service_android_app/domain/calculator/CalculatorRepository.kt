package com.lydone.okna_service_android_app.domain.calculator

import com.lydone.okna_service_android_app.domain.calculator.data.WindowSizeLimits
import com.lydone.okna_service_android_app.models.data.TestBean
import com.lydone.okna_service_android_app.presentation.calculator.model.WindowSashesCount
import retrofit2.Call

interface CalculatorRepository {
    suspend fun testRequest(): List<TestBean>

    suspend fun getWindowSizeLimits(windowSashesCount: WindowSashesCount) : WindowSizeLimits
}