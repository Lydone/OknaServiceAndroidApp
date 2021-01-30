package com.lydone.okna_service_android_app.domain.calculator

import com.lydone.okna_service_android_app.domain.calculator.model.CalculatorParams
import com.lydone.okna_service_android_app.domain.calculator.model.WindowDimensionsLimits
import com.lydone.okna_service_android_app.domain.calculator.model.WindowType

interface CalculatorRepository {

    suspend fun getWindowSizeLimits(sashesCount: Int) : WindowDimensionsLimits

    suspend fun getOverallWindowDimensionsLimits(): WindowDimensionsLimits

    suspend fun getMatchingWindowTypes(width: Int, height: Int): List<WindowType>

    suspend fun getPrice(calculatorParams: CalculatorParams): Int
}