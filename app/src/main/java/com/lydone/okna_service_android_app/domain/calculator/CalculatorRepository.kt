package com.lydone.okna_service_android_app.domain.calculator

import com.lydone.okna_service_android_app.domain.calculator.data.WindowSizeLimits

interface CalculatorRepository {

    suspend fun getWindowSizeLimits(sashesCount: Int) : WindowSizeLimits
}