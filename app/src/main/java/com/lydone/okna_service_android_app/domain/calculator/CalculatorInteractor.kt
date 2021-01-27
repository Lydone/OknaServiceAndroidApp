package com.lydone.okna_service_android_app.domain.calculator

import javax.inject.Inject

class CalculatorInteractor @Inject constructor(private val calculatorRepository: CalculatorRepository) {

    suspend fun getWindowSizeLimits(sashesCount: Int) = calculatorRepository.getWindowSizeLimits(sashesCount)

    suspend fun getOverallWindowDimensionsLimits() = calculatorRepository.getOverallWindowDimensionsLimits()
}