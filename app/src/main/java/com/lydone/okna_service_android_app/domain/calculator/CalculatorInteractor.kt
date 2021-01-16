package com.lydone.okna_service_android_app.domain.calculator

import com.lydone.okna_service_android_app.presentation.calculator.model.WindowSashesCount
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CalculatorInteractor @Inject constructor(private val calculatorRepository: CalculatorRepository) {

    suspend fun testRequest() = calculatorRepository.testRequest()

    suspend fun getWindowSizeLimits(windowSashesCount: WindowSashesCount) =
        calculatorRepository.getWindowSizeLimits(windowSashesCount)
}