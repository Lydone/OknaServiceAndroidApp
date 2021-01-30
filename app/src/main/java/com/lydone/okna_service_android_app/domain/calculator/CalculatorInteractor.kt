package com.lydone.okna_service_android_app.domain.calculator

import com.lydone.okna_service_android_app.domain.calculator.model.WindowModel
import javax.inject.Inject

class CalculatorInteractor @Inject constructor(private val calculatorRepository: CalculatorRepository) {

    suspend fun getWindowSizeLimits(sashesCount: Int) = calculatorRepository.getWindowSizeLimits(sashesCount)

    suspend fun getOverallWindowDimensionsLimits() = calculatorRepository.getOverallWindowDimensionsLimits()

    suspend fun getMatchingWindowTypes(width: Int, height: Int) =
        calculatorRepository.getMatchingWindowTypes(width, height)

    suspend fun getPrice(windowModel: WindowModel) = calculatorRepository.getPrice(windowModel)
}