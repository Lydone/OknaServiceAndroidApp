package com.lydone.okna_service_android_app.domain.calculator

import com.lydone.okna_service_android_app.domain.calculator.model.WindowDimensionsLimits

interface CalculatorRepository {

    suspend fun getWindowSizeLimits(sashesCount: Int) : WindowDimensionsLimits

    suspend fun getOverallWindowDimensionsLimits(): WindowDimensionsLimits
}