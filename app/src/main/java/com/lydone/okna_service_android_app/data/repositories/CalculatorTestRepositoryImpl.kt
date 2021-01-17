package com.lydone.okna_service_android_app.data.repositories

import com.lydone.okna_service_android_app.domain.calculator.CalculatorRepository
import com.lydone.okna_service_android_app.domain.calculator.model.WindowSizeLimits
import javax.inject.Inject

class CalculatorTestRepositoryImpl @Inject constructor() : CalculatorRepository {
    override suspend fun getWindowSizeLimits(sashesCount: Int) = when (sashesCount) {
        1 -> WindowSizeLimits(400, 3000, 400, 3000)
        2 -> WindowSizeLimits(900, 2000, 500, 2000)
        3 -> WindowSizeLimits(1400, 3000, 600, 3000)
        else -> throw IllegalArgumentException("Incorrect sashes count: $sashesCount")
    }
}